package com.cms.auth.config.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign日志打印,需要配置logging.level一起使用
 * @author DT辰白 Created by 2022/4/22 9:43
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLeave() {
        return Logger.Level.FULL;
    }
}
