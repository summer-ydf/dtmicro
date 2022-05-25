package com.cms.provider;


import com.cms.common.tool.utils.SysCmsUtils;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author DT辰白 Created by 2022/5/17 16:53
 */
@SpringBootApplication
@EnableDubbo // 开启dubbo
@EnableAsync
@EnableSwagger2
@EnableTransactionManagement
@MapperScan(basePackages = {"com.cms.provider.mapper"})
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$Dubbo服务提供方已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
