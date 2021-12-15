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
public class TokenConfig {

    /**
     * 密钥
     */
    public static final String SIGNING_KET = "uaa123";

    @Bean
    public TokenStore tokenStore() {
        // 采用内存的方式
        return new InMemoryTokenStore();
        // JWT令牌存储方案
        //return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对称密钥，资源服务器使用该密钥来验证
        converter.setSigningKey(SIGNING_KET);
        return converter;
    }
}
