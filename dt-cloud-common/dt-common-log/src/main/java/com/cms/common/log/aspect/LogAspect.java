package com.cms.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.cms.common.core.utils.ApiCallUtils;
import com.cms.common.core.utils.CoreWebUtils;
import com.cms.common.core.utils.ServletUtils;
import com.cms.common.log.annotation.Log;
import com.cms.common.log.service.AsyncLogService;
import com.cms.common.tool.constant.ConstantCommonCode;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志切面配置类
 * @author DT
 * @date 2021/11/20 15:15
 */
@Aspect
@Component
public class LogAspect {

    private final AsyncLogService asyncLogService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public LogAspect(AsyncLogService asyncLogService) {
        this.asyncLogService = asyncLogService;
    }

    /**
     * 定义切入点，以@annotation（注解）的方式切入
     */
    @Pointcut("@annotation(com.cms.common.log.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 业务方法处理完成之后执行
     * @param joinPoint 切点对象
     * @param resultValue 响应返回值
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "resultValue")
    public void doAfterReturning(JoinPoint joinPoint, Object resultValue) {
        handleLogInfo(joinPoint, null, resultValue);
    }

    /**
     * 增强处理程序中未处理的异常
     * @param joinPoint 切点对象
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLogInfo(joinPoint, e, null);
    }

    /**
     * 处理日志信息的方法
     * @param joinPoint 切点对象
     * @param e 异常
     * @param resultValue 响应返回值
     */
    protected void handleLogInfo(final JoinPoint joinPoint, final Exception e, Object resultValue) {
        try {
            // 记录操作日志
            SysOperatorLogVoEntity sysLog = new SysOperatorLogVoEntity();
            // 判断是否存在@Log注解，如果存在就获取注解信息
            Log log = existLogAnnotation(joinPoint);
            if(!ObjectUtils.isEmpty(log)) {
                // 业务操作类型
                sysLog.setBusinessType(String.valueOf(log.businessType()));
                // 业务操作标题
                sysLog.setTitle(log.title());
                // 获取参数的信息。
                getRequestParams(joinPoint, sysLog);
                // 操作状态默认正常
                sysLog.setStatus(ConstantCommonCode.INT_ONE);
                // 请求地址
                sysLog.setRequestUrl(ServletUtils.getRequest().getRequestURI());
                // 请求IP
                sysLog.setRequestIp(CoreWebUtils.getIpAddress(ServletUtils.getRequest()));
                // 响应返回参数
                sysLog.setResponseParam(JSON.toJSONString(resultValue));
                // 操作用户
                SecurityClaimsUserEntity securityClaimsUserEntity = null;
                try {
                    securityClaimsUserEntity = ApiCallUtils.securityClaimsUser(ServletUtils.getRequest());
                    sysLog.setRequestUserName(securityClaimsUserEntity.getUsername());
                } catch (ResultException ex) {
                    sysLog.setRequestUserName("未知用户");
                    ex.printStackTrace();
                }
                // 请求方法完整名称
                String className = joinPoint.getTarget().getClass().getName();
                String methodName = joinPoint.getSignature().getName();
                sysLog.setRequestMethodName(className + "." + methodName + "()");
                // 请求方式
                sysLog.setRequestMethodType(ServletUtils.getRequest().getMethod());
                if (!ObjectUtils.isEmpty(e)) {
                    // 操作异常
                    sysLog.setStatus(ConstantCommonCode.INT_TWO);
                    sysLog.setErrorInfo(e.getMessage());
                }
                // 保存操作日志到数据库
                asyncLogService.save(sysLog);
                logger.info("===============日志备份写入完成:{}",sysLog.getTitle());
            }
        } catch (Exception exp) {
            logger.error("===============系统操作日志异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 校验是否存在@Log注解
     * @param joinPoint 切点对象
     * @return 返回注解信息
     */
    private Log existLogAnnotation(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取方法请求参数
     * @param joinPoint 切点对象
     * @param sysLog 操作日志对象
     */
    private void getRequestParams(JoinPoint joinPoint, SysOperatorLogVoEntity sysLog) {
        String requestMethodType = ServletUtils.getRequest().getMethod();
        System.out.println();
        if (HttpMethod.PUT.name().equals(requestMethodType) || HttpMethod.POST.name().equals(requestMethodType)
                || HttpMethod.DELETE.name().equals(requestMethodType)) {
            sysLog.setRequestParam(argsArrayToString(joinPoint.getArgs()));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            sysLog.setRequestParam(paramsMap.toString());
        }
    }

    /**
     * 参数拼装处理返回
     * @param paramsArray 参数数组对象
     * @return 返回参数
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!isFilterObject(o)) {
                    Object jsonObj = JSON.toJSON(o);
                    params.append(jsonObj.toString()).append(" ");
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 是否需要过滤的对象
     * @param object 对象信息
     * @return boolean
     */
    public boolean isFilterObject(final Object object) {
        return object instanceof MultipartFile || object instanceof HttpServletRequest || object instanceof HttpServletResponse;
    }

}
