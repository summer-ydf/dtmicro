package com.cms.oauth.security.custom;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;

/**
 * 将授权用户放在线程里面，利用ThreadLocal来获取当前的用户对象
 * SecurityOAuth2AuthenticationHolder.getAuthentication();
 * @author DT辰白 Created by 2022/5/5 14:23
 */
public class SecurityOAuth2AuthenticationHolder {

    private static final ThreadLocal<OAuth2Authentication> TOKEN_HOLDER = new ThreadLocal<>();

    public static void clearAuthentication() {
        TOKEN_HOLDER.remove();
    }

    public static OAuth2Authentication getAuthentication() {
        return TOKEN_HOLDER.get();
    }

    public static void setAuthentication(OAuth2Authentication token) {
        Assert.notNull(token, "Only non-null OAuth2AccessToken instances are permitted");
        TOKEN_HOLDER.set(token);
    }
}
