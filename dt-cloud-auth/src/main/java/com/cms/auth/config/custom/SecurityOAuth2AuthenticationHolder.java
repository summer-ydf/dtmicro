package com.cms.auth.config.custom;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;

/**
 * @author ydf Created by 2022/1/7 14:08
 */
public class SecurityOAuth2AuthenticationHolder {

    private static final ThreadLocal<OAuth2Authentication> tokenHolder = new ThreadLocal<>();

    public static void clearAuthentication() {
        tokenHolder.remove();
    }

    public static OAuth2Authentication getAuthentication() {
        return tokenHolder.get();
    }

    public static void setAuthentication(OAuth2Authentication token) {
        Assert.notNull(token, "Only non-null OAuth2AccessToken instances are permitted");
        tokenHolder.set(token);
    }
}
