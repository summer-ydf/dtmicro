package com.cms.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置
 * @author ydf Created by 2021/12/14 14:56
 */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
//        resource.resourceId("res1");
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        // 配置需要保护的资源路径
//        http.authorizeRequests().antMatchers("/**").access("#oauth2.hasScope('all')").anyRequest().authenticated();
//    }
//}
