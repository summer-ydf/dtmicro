package com.cms.auth.config.handler;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author DT辰白 Created by 2022/1/7 14:05
 */
@FunctionalInterface
public interface OAuth2AuthenticationSuccessHandler {

    void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, OAuth2Authentication authentication) throws IOException, ServletException;
}
