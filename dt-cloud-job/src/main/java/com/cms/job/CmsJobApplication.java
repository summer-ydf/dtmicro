package com.cms.job;

import com.cms.common.tool.utils.SysCmsUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ydf Created by 2021/11/25 11:49
 */
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
