package com.cms.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 核心模块主启动
 * @author ydf Created by 2021/11/23 14:54
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.cms.workflow.mapper"})
@EnableFeignClients(basePackages ={"com.api.*.feign"})
public class CmsManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsManageApplication.class,args);
    }
}
