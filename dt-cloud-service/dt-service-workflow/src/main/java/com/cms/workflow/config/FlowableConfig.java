package com.cms.workflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 开启配置文件扫描
 * @author ydf Created by 2021/12/1 16:43
 */
@Configuration
@ImportResource(locations = {"classpath:flowable.cfg.xml"})
public class FlowableConfig {
}
