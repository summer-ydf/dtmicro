package com.cms.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    @ResponseStatus
    public Object fallback(ServerWebExchange exchange) {
        return null;
        //return GatewayConstant.response(exchange, HttpStatus.OK, GatewayConstant.FALLBACK_TEXT, GatewayConstant.FALLBACK_JSON);
    }
}
