package com.cms.manage;

import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.modular.config.IdGeneratorConfig;
import com.cms.modular.config.PasswordEncoderConfig;
import com.cms.modular.config.Swagger2Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 核心模块主启动
 * @author ydf Created by 2021/11/23 14:54
 */
@SpringBootApplication(exclude = {SeataFeignClientAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableAsync
@EnableSwagger2
@Import({Swagger2Config.class, IdGeneratorConfig.class, PasswordEncoderConfig.class})
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

    @Bean("sysTaskExecutor")
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        SysCmsUtils.log.info("======>>>核心处理器数量:"+core);
        // 设置核心线程数
        executor.setCorePoolSize(16);
        // 设置最大线程数
        executor.setMaxPoolSize(16);
        // 除核心线程外的线程存活时间
        executor.setKeepAliveSeconds(3);
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(40);
        // 线程名称前缀
        executor.setThreadNamePrefix("cms-manage-pool-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
