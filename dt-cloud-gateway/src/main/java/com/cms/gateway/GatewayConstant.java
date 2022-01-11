package com.cms.gateway;

import com.alibaba.fastjson.JSON;
import com.cms.common.result.ResultEnum;
import com.cms.common.result.ResultUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class GatewayConstant {

    public static final byte[] UNAUTHORIZED_JSON = JSON.toJSONBytes(ResultUtil.error(ResultEnum.RESOURCE_OAUTH_EXP.getCode(),ResultEnum.RESOURCE_OAUTH_EXP.getMessage()));

    public static final byte[] UNAUTHORIZED_TEXT = ResultEnum.GATEWAY_TIMEOUT_ERROR.getMessage().getBytes(StandardCharsets.UTF_8);

    public static final byte[] UNAVAILABLE_JSON = JSON.toJSONBytes(ResultUtil.error(ResultEnum.SERVICE_UNAVAILABLE_ERROR.getCode(),ResultEnum.SERVICE_UNAVAILABLE_ERROR.getMessage()));

    public static final byte[] UNAVAILABLE_TEXT = ResultEnum.SERVICE_UNAVAILABLE_ERROR.getMessage().getBytes(StandardCharsets.UTF_8);

    public static Mono<Void> response(ServerWebExchange exchange,HttpStatus statusCode,byte[] html_bytes,byte[] json_bytes) {
        return Mono.defer(() -> {
            String accept = exchange.getRequest().getHeaders().getFirst(HttpHeaders.ACCEPT);
            if(accept.contains("text/html")){
                // 设置status
                exchange.getResponse().setStatusCode(statusCode);
                final ServerHttpResponse response = exchange.getResponse();
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(html_bytes);
                response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8");
                // 设置body
                return response.writeWith(Flux.just(buffer));
            }else {
                exchange.getResponse().setStatusCode(statusCode);
                final ServerHttpResponse response = exchange.getResponse();
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(json_bytes);
                response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
                return response.writeWith(Flux.just(buffer));
            }
        });
    }
}