package com.cms.workflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 流程模块主启动类
 * @author ydf Created by 2021/11/23 15:23
 */
@MapperScan(basePackages = {"com.cms.workflow.mapper"})
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication
public class CmsWorkFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsWorkFlowApplication.class,args);
    }
}
