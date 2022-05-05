package com.cms.oauth.security.model.mobile;

import com.cms.oauth.service.impl.MemberUserDetailsServiceImpl;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 短信验证码认证授权提供者
 * @author ydf Created by 2022/4/28 13:04
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();

        System.out.println("输入手机号："+mobile);
        System.out.println("输入验证码："+code);

//        String codeKey = AuthConstants.SMS_CODE_PREFIX + mobile;
//        String correctCode = redisTemplate.opsForValue().get(codeKey);
//        // 验证码比对
//        if (StrUtil.isBlank(correctCode) || !code.equals(correctCode)) {
//            throw new BizException("验证码不正确");
//        } else {
//            redisTemplate.delete(codeKey);
//        }

        UserDetails userDetails = ((MemberUserDetailsServiceImpl) userDetailsService).loadUserByMobile(mobile);
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(userDetails, authentication.getCredentials(), new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
