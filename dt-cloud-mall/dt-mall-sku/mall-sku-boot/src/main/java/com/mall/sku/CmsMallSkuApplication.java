package com.mall.sku;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ydf Created by 2021/12/24 10:12
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.mall.sku.mapper"})
public class CmsMallSkuApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsMallSkuApplication.class,args);
    }
}
