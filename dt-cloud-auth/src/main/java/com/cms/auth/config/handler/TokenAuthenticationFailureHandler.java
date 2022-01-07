package com.cms.auth.config.handler;

import com.cms.auth.config.exception.CmsOAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 账号密码错误异常处理
 * @author ydf Created by 2022/1/6 15:02
 */
public class TokenAuthenticationFailureHandler implements OAuth2AuthenticationFailureHandler {

    @Override
    public CmsOAuth2Exception onAuthenticationFailure(OAuth2Exception oAuth2Exception) {
        CmsOAuth2Exception ex = new CmsOAuth2Exception(oAuth2Exception.getMessage(), oAuth2Exception);
        System.out.println("账号密码错误异常处理->>>");
        System.out.println(oAuth2Exception.getMessage());
        ex.setOauth2ErrorCode("账号或者密码错误");
        // TODO 加入Redis匹配超时账号锁定校验
        return ex;
    }
}
