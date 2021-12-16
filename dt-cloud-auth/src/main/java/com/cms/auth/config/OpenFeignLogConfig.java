package com.cms.auth.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign配置
 * @author ydf Created by 2021/12/16 11:42
 */
@Configuration
public class OpenFeignLogConfig {

    @Bean
    Logger.Level feignLoggerLeave() {
        return Logger.Level.FULL;
    }
}
