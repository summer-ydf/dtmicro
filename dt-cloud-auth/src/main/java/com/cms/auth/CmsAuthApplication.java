package com.cms.auth;
import com.cms.auth.config.handler.RestExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * OAuth2授权服务启动器
 * @author ydf Created by 2021/11/25 11:45
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
@Import({RestExceptionHandler.class})
@MapperScan(basePackages = {"com.cms.auth.mapper"})
@EnableHystrix
@EnableFeignClients(basePackages ={"com.cms.common.feign"})
public class CmsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsAuthApplication.class,args);
    }
}
