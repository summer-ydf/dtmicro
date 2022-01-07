package com.cms.gateway.controller;

import com.raspberry.icc.gateway.GatewayConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author lihao
 * @version 1.0.0
 * @ClassName FallbackController.java
 * @createTime 2021年12月23日 09:29:00
 * @description
 */
@RestController
public class FallbackController {
    @RequestMapping("/fallback")
    @ResponseStatus
    public Object fallback(ServerWebExchange exchange){
        return GatewayConstant.response(exchange, HttpStatus.OK, GatewayConstant.FALLBACK_TEXT, GatewayConstant.FALLBACK_JSON);
    }
}
