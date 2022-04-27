package com.cms.oauth.security.handler;

import com.cms.oauth.security.exception.CmsOAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author ydf Created by 2022/1/6 15:00
 */
public interface OAuth2AuthenticationFailureHandler {

    CmsOAuth2Exception onAuthenticationFailure(OAuth2Exception exception);

    CmsOAuth2Exception UnsupportedGrantTypeFailure(OAuth2Exception exception);

    CmsOAuth2Exception InvalidScopeFailure(OAuth2Exception exception);
}
