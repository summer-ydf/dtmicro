package com.cms.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author ydf Created by 2021/11/25 11:45
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.api.*.feign"})
public class CmsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsAuthApplication.class,args);
    }
}
