package com.cms.gateway.config;

import com.cms.gateway.exception.RestExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader;
import org.springframework.web.reactive.config.WebFluxConfigurer;

//@Configuration
//public class WebConfig implements WebFluxConfigurer {
//
//    @Override
//    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
//        System.out.println("WebFluxConfigurer->>>"+configurer);
//        SynchronossPartHttpMessageReader partReader = new SynchronossPartHttpMessageReader();
//        partReader.setMaxParts(1);
//        partReader.setMaxDiskUsagePerPart(100L * 1024L);
//        partReader.setEnableLoggingRequestDetails(true);
//
//        MultipartHttpMessageReader multipartReader = new MultipartHttpMessageReader(partReader);
//        multipartReader.setEnableLoggingRequestDetails(true);
//
//        configurer.defaultCodecs().multipartReader(multipartReader);
//    }
//    @Primary
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ErrorWebExceptionHandler errorWebExceptionHandler() {
//        return new RestExceptionHandler();
//    }
//}