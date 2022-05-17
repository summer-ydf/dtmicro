package com.cms.consumer;

import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ydf Created by 2022/5/17 16:53
 */
@SpringBootApplication
public class DubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$Dubbo服务消费方已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
