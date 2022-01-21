package com.cms.auth.config.filter;

import com.cms.auth.exception.ParameterAuthenticationException;
import com.cms.common.constant.ConstantCommonCode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author ydf Created by 2022/1/21 17:55
 */
public class CmsLockAuthenticationFilter extends CmsAbstractBasicAuthenticationFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if(StringUtils.isEmpty(username)) {throw new ParameterAuthenticationException("缺少参数"); }
        if(StringUtils.isEmpty(password)) {throw new ParameterAuthenticationException("缺少参数"); }
        username = username.trim();
        String login_count_str = stringRedisTemplate.opsForValue().get(ConstantCommonCode.CACHE_LOGIN_LOCK + username);
        long login_count = NumberUtils.toLong(login_count_str,0);
        if(login_count == 5) {
            Date exp_date = new Date(login_count);
            if(new Date().before(exp_date)) {
                throw new ParameterAuthenticationException("账户被锁定，请稍后登录");
            }
        }
    }
}
