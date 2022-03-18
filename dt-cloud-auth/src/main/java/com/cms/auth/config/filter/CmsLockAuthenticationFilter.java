package com.cms.auth.config.filter;

import com.cms.auth.exception.ParameterAuthenticationException;
import com.cms.common.tool.constant.ConstantCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账号锁定校验
 * @author ydf Created by 2022/1/21 17:55
 */
public class CmsLockAuthenticationFilter extends CmsAbstractBasicAuthenticationFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(StringUtils.isEmpty(username)) {throw new ParameterAuthenticationException("缺少username参数"); }
        if(StringUtils.isEmpty(password)) {throw new ParameterAuthenticationException("缺少password参数"); }
        String loginCountStr = stringRedisTemplate.opsForValue().get(ConstantCode.CACHE_LOGIN_LOCK + username.trim());
        if(loginCountStr != null && loginCountStr.length() > 1) {
            if(System.currentTimeMillis() < Long.parseLong(loginCountStr)) {
                throw new ParameterAuthenticationException("账户被锁定15分钟，请稍后登录!");
            }
        }
    }
}
