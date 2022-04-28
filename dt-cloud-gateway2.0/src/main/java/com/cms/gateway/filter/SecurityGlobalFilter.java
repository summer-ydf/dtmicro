package com.cms.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cms.common.tool.result.ResultEnum;
import com.cms.gateway.GatewayConstant;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sun.security.util.SecurityConstants;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 网关拦截器配置类
 * @author ydf Created by 2022/4/26 10:21
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.profiles.active}")
    private String env;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String requestPath = exchange.getRequest().getPath().toString();
        log.info("项目运行环境【{}】",env);
        log.info("当前请求路径【{}】",requestPath);
        // TODO 线上演示环境修改和删除行为的接口禁止操作
        if (env.equals("prod")) {
            return GatewayConstant.responseErrorJson(exchange, ResultEnum.PROHIBIT_OPERATION);
        }
        // TODO 错误的JWT不做解析处理
        String token = request.getHeaders().getFirst("Authorization");
        System.out.println("当前请求Token："+token);
        // 如果当前请求未携带token令牌，则说明是白名单路径，直接放行
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token,"Bearer ")) {
            return chain.filter(exchange);
        }
        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
        token = StrUtil.replaceIgnoreCase(token, "Bearer ", Strings.EMPTY);
        System.out.println("1>解析JWT获取jti："+token);
        String payload = StrUtil.toString(JWSObject.parse(token).getPayload());
        System.out.println("2>解析JWT获取jti："+payload);
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        String jti = jsonObject.getStr("jti");
        System.out.println("3>获取到jti唯一标识："+jti);

        // JWT过期时间戳(单位：秒)
        Long expireTime = jsonObject.getLong("exp");
        // 当前时间（单位：秒）
        long currentTime = System.currentTimeMillis() / 1000;
        System.out.println("4>JWT过期时间戳：" + (expireTime - currentTime));
        Boolean isExist = redisTemplate.hasKey("auth:token:blacklist:" + jti);
        if (isExist) {
            System.out.println("不存在返回测试============================================");
            return GatewayConstant.responseErrorJson(exchange, ResultEnum.TOKEN_ACCESS_FORBIDDEN);
        }
        // 存在token且不是黑名单，request写入JWT的载体信息
        request = exchange.getRequest().mutate()
                .header("payload", URLEncoder.encode(payload, "UTF-8"))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
