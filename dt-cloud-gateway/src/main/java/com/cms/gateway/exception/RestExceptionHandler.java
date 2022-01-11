package com.cms.gateway.exception;

import com.cms.gateway.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

/**
 * 网关异常通用处理器，只作用在webflux 环境下
 */
@Slf4j
public class RestExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if(ex instanceof NotFoundException){
            log.warn("not found ex->{}",ex.getMessage());
            return GatewayConstant.response(exchange, HttpStatus.SERVICE_UNAVAILABLE, GatewayConstant.UNAVAILABLE_TEXT, GatewayConstant.UNAVAILABLE_JSON);
        }else {
            log.warn("捕捉到异常:",ex);
        }
        return Mono.empty();
    }
}