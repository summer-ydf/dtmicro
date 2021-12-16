package com.cms.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 核心模块主启动
 * @author ydf Created by 2021/11/23 14:54
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.cms.item.mapper"})
public class CmsItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsItemApplication.class,args);
    }
}
