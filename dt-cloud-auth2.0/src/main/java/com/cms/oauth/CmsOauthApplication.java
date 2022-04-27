package com.cms.oauth;

import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.oauth.security.handler.RestExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * OAuth2授权服务启动器
 * @author ydf Created by 2021/11/25 11:45
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
@EnableSwagger2
@Import({RestExceptionHandler.class, IdGeneratorConfig.class})
@MapperScan(basePackages = {"com.cms.oauth.mapper"})
@EnableFeignClients(basePackages ={"com.api.manage.feign"})
public class CmsOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsOauthApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$授权服务2.0版本已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
