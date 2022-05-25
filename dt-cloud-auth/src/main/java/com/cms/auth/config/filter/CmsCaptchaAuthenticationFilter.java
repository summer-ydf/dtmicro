package com.cms.auth.config.filter;

import com.cms.auth.exception.ParameterAuthenticationException;
import com.cms.common.jdbc.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.cms.common.tool.constant.ConstantCode.CACHE_CODE_KEY;

/**
 * 验证码校验
 * @author DT辰白 Created by 2022/1/6 14:09
 */
public class CmsCaptchaAuthenticationFilter extends CmsAbstractBasicAuthenticationFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String code = request.getParameter("valid_code");
        if(StringUtils.isBlank(code)) {
            throw new ParameterAuthenticationException("缺少验证码参数");
        }
        this.validCode(code);
    }

    private void validCode(String code) {
        String inputCode = (CACHE_CODE_KEY + code).toLowerCase();
        Object redisCode = redisUtils.get((CACHE_CODE_KEY + code).toLowerCase());
        if (code.equalsIgnoreCase((String) redisCode)) {
            redisUtils.del(inputCode);
        } else {
            throw new ParameterAuthenticationException("验证码错误");
        }
    }
}
