package com.cms.auth.config.exception;

import com.alibaba.fastjson.JSON;
import com.cms.auth.exception.ParameterAuthenticationException;
import com.cms.common.result.ResultEnum;
import com.cms.common.result.ResultUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
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
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),ex.getMessage());
        } else if (ex instanceof InsufficientAuthenticationException) {
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),"认证错误");
        }
//        else if (ex instanceof TokenAuthenticationException) {
//            return ApiResponseHelper.response(null, IcpError.REQUEST_OAUTH_EXP.getCode(),IcpError.REQUEST_OAUTH_EXP.getMessage());
//        }
        else if (ex instanceof IccOAuth2Exception) {
            // client_id和secret的验证
            IccOAuth2Exception exception = (IccOAuth2Exception)ex;
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),exception.getOauth2ErrorCode());
        }
        return null;
    }

    private ResultUtil<?> loginResponseObject(Throwable ex) {
        ResultUtil<?> customException = customException(null, null, ex);
        if(customException == null) {
            System.out.println("为null==================");
            return ResultUtil.error(ResultEnum.OAuth2Exception.getCode(),ResultEnum.OAuth2Exception.getMessage());
        }
        return customException;
    }

    public void loginExceptionHandler(HttpServletRequest request,HttpServletResponse response,Throwable ex) {
        ResultUtil<?> apiResponse = loginResponseObject(ex);
        writeJSON(request,response, apiResponse);
        System.out.println("登录异常->>>");
        System.out.println("request->>>"+request);
        System.out.println("response->>>"+response);
        System.out.println("ex->>>"+ex);
    }

    public void loginExceptionHandler(JsonGenerator jgen, IccOAuth2Exception exception) throws IOException {
        jgen.writeStartObject();
        ResultUtil<?> apiResponse=loginResponseObject(exception);
        System.out.println("自定义输出->>>"+apiResponse);
        jgen.writeStringField("errmsg",apiResponse.getMessage());
        jgen.writeNumberField("errcode",apiResponse.getCode());
        jgen.writeEndObject();
    }

    public static void writeJSON(HttpServletRequest request, HttpServletResponse response, Object o) {
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
