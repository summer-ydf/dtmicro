package com.cms.gateway;

import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author DT
 * @date 2022/4/25 20:45
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CmsGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsGatewayServiceApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$网关服务2.0已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
