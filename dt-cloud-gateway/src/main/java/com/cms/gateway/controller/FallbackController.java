package com.cms.gateway.controller;

import com.cms.gateway.GatewayConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author DT辰白 Created by 2022/3/29 9:50
 */
@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    @ResponseStatus
    public Object fallback(ServerWebExchange exchange) {
        return GatewayConstant.response(exchange, HttpStatus.OK, GatewayConstant.UNAVAILABLE_TEXT, GatewayConstant.UNAVAILABLE_JSON);
    }
}
