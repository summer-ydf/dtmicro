package com.cms.auth.config.exception;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.cms.common.result.ResultEnum;
import com.cms.common.result.ResultUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源服务token失效处理器
 * @author DT
 * @date 2022/1/6 21:50
 */
@Component
public class CmsTokenExpireAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("Token失效处理："+authException);
        response.setStatus(HttpStatus.HTTP_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        ResultUtil<Object> error = ResultUtil.error(ResultEnum.RESOURCE_OAUTH_EXP.getCode(),
                ResultEnum.RESOURCE_OAUTH_EXP.getMessage());
        response.getWriter().print(JSONUtil.toJsonStr(error));
        response.getWriter().flush();
    }
}
