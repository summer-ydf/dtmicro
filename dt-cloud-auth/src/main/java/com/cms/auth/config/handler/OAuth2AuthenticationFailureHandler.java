package com.cms.auth.config.handler;

import com.cms.auth.config.exception.CmsOAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author DT辰白 Created by 2022/1/6 15:00
 */
@FunctionalInterface
public interface OAuth2AuthenticationFailureHandler {

    CmsOAuth2Exception onAuthenticationFailure(OAuth2Exception exception);
}
