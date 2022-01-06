package com.cms.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 令牌存储策略配置
 * @author DT
 * @date 2021/12/14 19:25
 */
@Configuration
public class AccessTokenConfig {

    @Bean
    public TokenStore tokenStore() {
        // TODO 采用内存的方式
        return new InMemoryTokenStore();
    }
}
