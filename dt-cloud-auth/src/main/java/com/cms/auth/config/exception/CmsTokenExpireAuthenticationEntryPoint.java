package com.cms.auth.config.exception;

import com.alibaba.fastjson.JSON;
import com.cms.common.tool.result.ResultEnum;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源服务token失效处理器
 * @author DT辰白
 * @date 2022/1/6 21:50
 */
@Component
public class CmsTokenExpireAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        SysCmsUtils.log.info("资源请求路径："+request.getRequestURI());
        SysCmsUtils.log.info("Token失效处理："+authException);
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        ResultUtil<Object> error = ResultUtil.error(ResultEnum.RESOURCE_OAUTH_EXP.getCode(), ResultEnum.RESOURCE_OAUTH_EXP.getMessage());
        response.getWriter().print(JSON.toJSONString(error));
        response.getWriter().flush();
    }
}
