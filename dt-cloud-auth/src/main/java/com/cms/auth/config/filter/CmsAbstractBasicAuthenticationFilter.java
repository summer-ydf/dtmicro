package com.cms.auth.config.filter;

import com.cms.auth.config.exception.RestExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ydf Created by 2022/1/6 14:05
 */
public abstract class CmsAbstractBasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 校验是否密码模式
        if (!StringUtils.equals(request.getRequestURI(),"/oauth/token") || !StringUtils.equals("password",request.getParameter("grant_type"))) {
            System.out.println("密码模式->>>");
            filterChain.doFilter(request, response);
            return;
        }
        try{
            handle(request,response,filterChain);
            filterChain.doFilter(request, response);
        }catch (Exception e){
            this.complete(request,response,e);
        }
    }
    protected void complete(HttpServletRequest request, HttpServletResponse response,Exception ex){
        restExceptionHandler.loginExceptionHandler(request,response,ex);
    }

    protected abstract void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception;
}
