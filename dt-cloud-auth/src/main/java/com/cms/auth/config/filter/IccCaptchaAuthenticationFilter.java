package com.cms.auth.config.filter;

import com.cms.auth.exception.ParameterAuthenticationException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ydf Created by 2022/1/6 14:09
 */
public class IccCaptchaAuthenticationFilter extends IccAbstractBasicAuthenticationFilter {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {
        String validCode = request.getParameter("validCode");
        if(StringUtils.isEmpty(validCode)) {
            throw new ParameterAuthenticationException("缺少参数");
        }
        // TODO 与redis校验
        //this.validCode(valid_code);
    }
}
