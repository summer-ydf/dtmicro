package com.cms.gateway;

import com.alibaba.fastjson.JSON;
import com.cms.common.result.ResultUtil;
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

    public enum IcpError {
        SYSTEM_ERROR(50000, "系统执行错误"),
        OAuth2Exception(50001, "认证错误"),
        SERVICE_NOT_FOUND(50002, "服务标识不存在"),
        BUILD_API_REQUEST_ERROR(50003, "构建参数错误"),
        REQUEST_PARAMS_ERROR(50004, "请求参数不合法，缺少必要数据"),
        REQUEST_APPID_NOT_MATCH(50005, "APPID不合法"),
        REQUEST_OAUTH_EXP(50006, "授权已过期"),
        REQUEST_NOT_SCOPE(50007, "资源访问受限"),
        TEMPLATE_NOT_FOUNT(50008, "未找到资源"),
        INSIDE_PARAMS_ERROR(50009, "报文格式错误"),
        UPLOAD_FILE_ERROR(50010, "文件上传失败"),
        SIGN_ERROR(50011, "签名错误"),
        Resource_not_found(50012, "未找到该应用"),
        valid_code_error(50013, "验证码错误"),
        valid_password_error(50014, "用户名或密码错误"),
        RPC_ERROR(50015, "远程服务异常"),
        RPC_HYSTRIX_ERROR(50016, "远程服务响应超时"),
        SERVICE_ERROR(50017, "业务错误"),
        Getaway_timeout(50018, "网关超时"),
        Username_not_found(50019, "未找到用户信息")
        ;
        private String message;
        private int code;

        IcpError(int code, String message) {
            this.message = message;
            this.code = code;
        }

        static public IcpError format(int code) {
            for (IcpError value : IcpError.values()) {
                if (code == value.code) {
                    return value;
                }
            }
            return IcpError.SYSTEM_ERROR;
        }

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }
    }

}