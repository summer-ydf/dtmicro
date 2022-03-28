package com.cms.job;

import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.tool.utils.SysCmsUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ydf Created by 2021/11/25 11:49
 */
@EnableSwagger2
@EnableTransactionManagement
@EnableDiscoveryClient
@Import({IdGeneratorConfig.class})
@MapperScan(basePackages = {"com.cms.job.mapper"})
@SpringBootApplication
public class CmsJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsJobApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$定时调度服务已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
