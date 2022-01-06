package com.cms.auth.config.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author ydf Created by 2022/1/6 15:00
 */
@FunctionalInterface
public interface OAuth2AuthenticationFailureHandler {

    CmsOAuth2Exception onAuthenticationFailure(OAuth2Exception exception);
}
