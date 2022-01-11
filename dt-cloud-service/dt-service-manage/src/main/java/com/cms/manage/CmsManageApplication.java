package com.cms.manage;

import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import com.cms.common.utils.SysCmsUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 核心模块主启动
 * @author ydf Created by 2021/11/23 14:54
 */
@SpringBootApplication(exclude = {SeataFeignClientAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.cms.manage.mapper"})
@EnableFeignClients(basePackages ={"com.api.*.feign"})
public class CmsManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsManageApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$管理服务已启动:===============");
        SysCmsUtils.log.info("============================================");
    }
}
