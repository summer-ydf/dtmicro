package com.mall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ydf Created by 2021/12/24 10:10
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.mall.user.mapper"})
public class CmsMallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsMallUserApplication.class,args);
    }
}
