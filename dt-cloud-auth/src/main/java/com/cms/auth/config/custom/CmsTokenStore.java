package com.cms.auth.config.custom;


import org.apache.commons.collections.MapUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.concurrent.TimeUnit;

import static com.cms.common.constant.ConstantCommonCode.CACHE_LOGIN_TOKEN;

/**
 * @author ydf Created by 2022/1/7 14:27
 */
public class CmsTokenStore extends JwtTokenStore {

    private final StringRedisTemplate stringRedisTemplate;

    public CmsTokenStore(JwtAccessTokenConverter jwtTokenEnhancer, StringRedisTemplate stringRedisTemplate) {
        super(jwtTokenEnhancer);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String jti = MapUtils.getString(token.getAdditionalInformation(),"jti");
        String claims = MapUtils.getString(token.getAdditionalInformation(),"claims");
        System.out.println("jti->>>"+jti);
        System.out.println("claims->>>"+claims);
        stringRedisTemplate.opsForValue().set(CACHE_LOGIN_TOKEN + jti,claims,token.getExpiresIn(), TimeUnit.SECONDS);
        SecurityOAuth2AuthenticationHolder.setAuthentication(authentication);
    }

    // 这里用DefaultOAuth2AccessToken的value来构造jti
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        stringRedisTemplate.delete(CACHE_LOGIN_TOKEN + token.getValue());
    }
}
