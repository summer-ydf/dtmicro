package com.cms.gateway;

import com.cms.common.utils.SysCmsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ydf Created by 2021/11/24 17:05
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsGatewayApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$网关服务已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
