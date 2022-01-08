package com.cms.auth.config;

import com.cms.auth.config.exception.CmsTokenExpireAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAuth2资源服务器配置
 * @author ydf Created by 2021/12/14 14:56
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private CmsTokenExpireAuthenticationEntryPoint cmsTokenExpireAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // token失效处理器
        resources.authenticationEntryPoint(cmsTokenExpireAuthenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置需要保护的资源路径
        http.authorizeRequests().antMatchers("/rsa/publicKey").permitAll();
        http.authorizeRequests().antMatchers("/**").access("#oauth2.hasScope('web')").anyRequest().authenticated();
    }
}
