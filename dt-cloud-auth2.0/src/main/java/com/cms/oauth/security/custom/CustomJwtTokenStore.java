package com.cms.oauth.security.custom;

import org.apache.commons.collections.MapUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.concurrent.TimeUnit;

import static com.cms.common.tool.constant.ConstantCode.CACHE_LOGIN_TOKEN;

/**
 * Token令牌存储策略，Redis存储
 * @author ydf Created by 2022/5/5 14:18
 */
public class CustomJwtTokenStore extends JwtTokenStore {

    private final RedisTemplate<String, Object> redisTemplate;
    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer, RedisTemplate<String, Object> redisTemplate) {
        super(jwtTokenEnhancer);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String jti = MapUtils.getString(token.getAdditionalInformation(),"jti");
        String userid = MapUtils.getString(token.getAdditionalInformation(),"userid");
        redisTemplate.opsForValue().set(CACHE_LOGIN_TOKEN + jti,userid,token.getExpiresIn(), TimeUnit.SECONDS);
        SecurityOAuth2AuthenticationHolder.setAuthentication(authentication);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        redisTemplate.delete(CACHE_LOGIN_TOKEN + token.getValue());
    }
}
