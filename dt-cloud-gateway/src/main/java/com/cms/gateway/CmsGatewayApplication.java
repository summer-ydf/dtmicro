package com.cms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ydf Created by 2021/11/24 17:05
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class CmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsGatewayApplication.class,args);
    }
}
