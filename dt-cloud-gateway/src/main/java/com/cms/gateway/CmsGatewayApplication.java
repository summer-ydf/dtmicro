package com.cms.gateway;

import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author DT辰白 Created by 2021/11/24 17:05
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsGatewayApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$网关服务V1.0版本已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
