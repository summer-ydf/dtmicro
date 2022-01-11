package com.cms.auth.config.handler;

import com.alibaba.fastjson.JSON;
import com.cms.auth.config.exception.CmsOAuth2Exception;
import com.cms.auth.config.exception.TokenAuthenticationException;
import com.cms.auth.exception.ParameterAuthenticationException;
import com.cms.common.result.ResultEnum;
import com.cms.common.result.ResultUtil;
import com.cms.common.utils.SysCmsUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author ydf Created by 2022/1/6 13:44
 */
public class RestExceptionHandler {

    public ResultUtil<?> customException(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),"用户名或密码错误");
        } else if (ex instanceof DisabledException) {
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),"账户被禁用");
        } else if (ex instanceof ParameterAuthenticationException) {
            return ResultUtil.error(ResultEnum.VALID_CODE_ERROR.getCode(),ex.getMessage());
        } else if (ex instanceof InsufficientAuthenticationException) {
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),"认证错误");
        } else if (ex instanceof TokenAuthenticationException) {
            return ResultUtil.error(ResultEnum.RESOURCE_OAUTH_EXP.getCode(),ResultEnum.RESOURCE_OAUTH_EXP.getMessage());
        } else if (ex instanceof CmsOAuth2Exception) {
            // 账号密码的验证
            CmsOAuth2Exception exception = (CmsOAuth2Exception)ex;
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),exception.getOauth2ErrorCode());
        }
        return null;
    }

    private ResultUtil<?> loginResponseObject(Throwable ex) {
        ResultUtil<?> customException = customException(null, null, ex);
        if(customException == null) {
            SysCmsUtils.log.info("未知异常捕获==================");
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),ResultEnum.OAuth2Exception.getMessage());
        }
        return customException;
    }

    public void loginExceptionHandler(HttpServletRequest request,HttpServletResponse response,Throwable ex) {
        ResultUtil<?> apiResponse = loginResponseObject(ex);
        writeToJson(request,response, apiResponse);
    }

    public void loginExceptionHandler(JsonGenerator jgen, CmsOAuth2Exception exception) throws IOException {
        jgen.writeStartObject();
        ResultUtil<?> apiResponse = loginResponseObject(exception);
        jgen.writeNumberField("code",apiResponse.getCode());
        jgen.writeStringField("message",apiResponse.getMessage());
        // 非密码模式输出
        if(exception.getOauth2ErrorCode().equals(OAuth2Exception.UNSUPPORTED_GRANT_TYPE)) {
            jgen.writeNumberField("code",ResultEnum.OAUTH2_GRANTTYPE_ERROR.getCode());
            jgen.writeStringField("message",ResultEnum.OAUTH2_GRANTTYPE_ERROR.getMessage());
        }
        jgen.writeBooleanField("success",false);
        jgen.writeNumberField("timestamp",System.currentTimeMillis());
        jgen.writeEndObject();
    }

    public static void writeToJson(HttpServletRequest request, HttpServletResponse response, Object o) {
        Writer writer = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(o));
            writer.flush();
        } catch (IOException ignored) {
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
