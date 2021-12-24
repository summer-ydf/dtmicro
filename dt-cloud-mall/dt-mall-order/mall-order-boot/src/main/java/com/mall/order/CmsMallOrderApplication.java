package com.mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ydf Created by 2021/12/24 10:13
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.sku.*.feign","com.user.*.feign"})
@MapperScan(basePackages = {"com.mall.order.mapper"})
public class CmsMallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsMallOrderApplication.class,args);
    }
}
