package com.cms.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 流程模块主启动类
 * @author ydf Created by 2021/11/23 15:23
 */
@EnableDiscoveryClient
@SpringBootApplication
public class WorkFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkFlowApplication.class,args);
    }
}
