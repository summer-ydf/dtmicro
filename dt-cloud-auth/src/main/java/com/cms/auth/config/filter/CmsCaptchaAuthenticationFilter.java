package com.cms.auth.config.filter;

import com.cms.auth.exception.ParameterAuthenticationException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码校验
 * @author ydf Created by 2022/1/6 14:09
 */
public class CmsCaptchaAuthenticationFilter extends CmsAbstractBasicAuthenticationFilter {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {
        String validCode = request.getParameter("validCode");
        if(StringUtils.isEmpty(validCode)) {
            throw new ParameterAuthenticationException("缺少验证码参数");
        }
        // TODO 与redis校验
    }
}