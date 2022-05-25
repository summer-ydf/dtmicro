package com.cms.oauth.security.model.idcard;

import com.cms.oauth.service.impl.IdCardUserDetailsServiceImpl;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 身份证认证授权提供者
 * @author DT辰白 Created by 2022/4/28 13:08
 */
@Data
public class IdCardAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        IdCardAuthenticationToken authenticationToken = (IdCardAuthenticationToken) authentication;
        String idno = (String) authenticationToken.getPrincipal();
        String name = (String) authenticationToken.getCredentials();

        System.out.println("输入身份证："+idno);
        System.out.println("输入姓名："+name);

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
