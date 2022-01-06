package com.cms.auth.config.exception;

import com.cms.auth.config.filter.IccAbstractBasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ydf Created by 2022/1/6 15:30
 */
public class IccBasicAuthenticationFilter extends IccAbstractBasicAuthenticationFilter {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {
        System.out.println("oauth2基础信息校验->>>");
    }
}
