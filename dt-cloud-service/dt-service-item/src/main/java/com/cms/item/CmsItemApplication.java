package com.cms.item;

import com.cms.common.utils.SysCmsUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 核心模块主启动
 * @author ydf Created by 2021/11/23 14:54
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.cms.item.mapper"})
public class CmsItemApplication implements DisposableBean {

    private final Environment environment;

    public CmsItemApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(CmsItemApplication.class,args);
    }

    @EventListener
    public void deployEventListener(ApplicationReadyEvent event) {
        SysCmsUtils.log.info("程序触发上下文监听完成事件....");
        //CanalClientInstance.getInstance(environment).start();
    }

    @Override
    public void destroy() {
        SysCmsUtils.log.info("程序触发销毁事件....");
        //CanalClientInstance.getInstance(environment).stop();
    }
}