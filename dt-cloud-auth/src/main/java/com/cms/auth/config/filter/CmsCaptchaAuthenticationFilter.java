package com.cms.auth.config.filter;

import com.alibaba.fastjson.JSON;
import com.cms.auth.domain.SecurityClaimsParams;
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
 * @author ydf Created by 2022/1/6 14:09
 */
public class CmsCaptchaAuthenticationFilter extends CmsAbstractBasicAuthenticationFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String claims = request.getParameter("claims");
        String code = request.getParameter("valid_code");
        if(StringUtils.isEmpty(code)) {
            throw new ParameterAuthenticationException("缺少验证码参数");
        }
        if(StringUtils.isEmpty(claims)) {
            throw new ParameterAuthenticationException("缺少附加参数");
        }
        this.validCode(code);
        SecurityClaimsParams params = JSON.parseObject(claims,SecurityClaimsParams.class);
        request.setAttribute(SecurityClaimsParams.class.getName(),params);
    }

    private void validCode(String code) {
        String inputCode = (CACHE_CODE_KEY + code).toLowerCase();
        Object redisCode = redisUtils.get((CACHE_CODE_KEY + code).toLowerCase());
        if (!inputCode.equalsIgnoreCase((String) redisCode)) {
            redisUtils.del(inputCode);
        } else {
            throw new ParameterAuthenticationException("验证码错误");
        }
    }
}
