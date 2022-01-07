package com.cms.auth.config.interceptor;

import com.cms.auth.config.handler.OAuth2AuthenticationSuccessHandler;
import com.cms.auth.config.custom.SecurityOAuth2AuthenticationHolder;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ydf Created by 2022/1/7 14:05
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    public AuthorizationInterceptor(OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        OAuth2Authentication authentication = SecurityOAuth2AuthenticationHolder.getAuthentication();
        System.out.println("authentication->>>"+authentication);
        if(authentication != null) {
            try{
                oAuth2AuthenticationSuccessHandler.onAuthenticationSuccess(request,response,authentication);
            }finally {
                SecurityOAuth2AuthenticationHolder.clearAuthentication();
            }
        }
    }
}
