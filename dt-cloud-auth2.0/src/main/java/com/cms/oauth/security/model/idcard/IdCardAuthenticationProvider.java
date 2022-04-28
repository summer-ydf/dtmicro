package com.cms.oauth.security.model.idcard;

import com.cms.oauth.security.model.mobile.SmsCodeAuthenticationToken;
import com.cms.oauth.service.impl.IdCardUserDetailsServiceImpl;
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
 * 身份证认证授权提供者
 * @author ydf Created by 2022/4/28 13:08
 */
@Data
public class IdCardAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    //private MemberFeignClient memberFeignClient;
//    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        IdCardAuthenticationToken authenticationToken = (IdCardAuthenticationToken) authentication;
        String idno = (String) authenticationToken.getPrincipal();
        String name = (String) authenticationToken.getCredentials();

        System.out.println("输入身份证："+idno);
        System.out.println("输入姓名："+name);

//        String codeKey = AuthConstants.SMS_CODE_PREFIX + mobile;
//        String correctCode = redisTemplate.opsForValue().get(codeKey);
//        // 验证码比对
//        if (StrUtil.isBlank(correctCode) || !code.equals(correctCode)) {
//            throw new BizException("验证码不正确");
//        } else {
//            redisTemplate.delete(codeKey);
//        }

        UserDetails userDetails = ((IdCardUserDetailsServiceImpl) userDetailsService).loadUserByIdCard(idno,name);
        IdCardAuthenticationToken result = new IdCardAuthenticationToken(userDetails, authentication.getCredentials(), new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return IdCardAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
