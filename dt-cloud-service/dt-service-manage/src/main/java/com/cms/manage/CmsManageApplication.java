package com.cms.manage;

import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.builder.SettingModelBuilders;
import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.tool.utils.SysCmsUtils;
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
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
@EnableSwagger2
@EnableTransactionManagement
@EnableDiscoveryClient
@Import({IdGeneratorConfig.class})
@MapperScan(basePackages = {"com.cms.manage.mapper"})
@EnableFeignClients(basePackages ={"com.api.*.feign"})
public class CmsManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsManageApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$管理服务已启动:===============");
        SysCmsUtils.log.info("============================================");
    }

    static {
        SettingBuilders.addModels("s1","系统配置", new SettingModelBuilders()
                .buildModel("server.url","系统内网地址","http://ip:port")
                .buildModel("flow.ff.zp.count","发放流程转批最大次数","发放流程转批最大次数,最大3次")
                .buildModel("flow.ff.zp.open","是否发放流程转批","1是，0否")
                .buildModel("flow.seal.type","签章类型","1服务器签章，2本地UK签章,默认服务器")
                .buildModel("flow.seal.api","服务器签章接口地址","服务器签章接口地址")
                .buildModel("flow.seal.url","服务器签章URL","服务器签章URL")
                .buildModel("flow.sign.client.port","本地UK签章端口","本地UK签章端口")
                .buildModel("flow.wx.template.id","流程微信通知模板ID","流程微信通知模板ID")
        );
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
