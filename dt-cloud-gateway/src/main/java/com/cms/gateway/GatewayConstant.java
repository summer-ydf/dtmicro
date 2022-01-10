package com.cms.gateway;

import com.alibaba.fastjson.JSON;
import com.cms.common.result.ResultUtil;
import com.cms.gateway.config.IcpError;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * Created by macro on 2020/6/19.
 */
public class GatewayConstant {
    public static final byte[] UNAUTHORIZED_JSON = JSON.toJSONBytes(ResultUtil.error(201,"授权已过期"));

    public static final byte[] UNAUTHORIZED_TEXT = IcpError.REQUEST_OAUTH_EXP.getMessage().getBytes(StandardCharsets.UTF_8);

    public static final byte[] FALLBACK_JSON = JSON.toJSONBytes(ResultUtil.error(201,"网关超时"));

    public static final byte[] FALLBACK_TEXT = IcpError.Getaway_timeout.getMessage().getBytes(StandardCharsets.UTF_8);

    public static Mono<Void> response(ServerWebExchange exchange,HttpStatus statusCode,byte[] html_bytes,byte[] json_bytes) {
        return Mono.defer(() -> {
            String accept = exchange.getRequest().getHeaders().getFirst(HttpHeaders.ACCEPT);
            if(accept.contains("text/html")){
                exchange.getResponse().setStatusCode(statusCode);//设置status
                final ServerHttpResponse response = exchange.getResponse();
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(html_bytes);
                response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8");
                return response.writeWith(Flux.just(buffer));//设置body
            }else {
                exchange.getResponse().setStatusCode(statusCode);//设置status
                final ServerHttpResponse response = exchange.getResponse();
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(json_bytes);
                response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
                return response.writeWith(Flux.just(buffer));//设置body
            }
        });
    }
}