package com.cms.gateway.filter;


import com.cms.common.utils.SysCmsUtils;
import com.cms.gateway.GatewayConstant;
import com.cms.gateway.config.IgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author ydf Created by 2022/1/7 17:52
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        RequestPath requestPath = exchange.getRequest().getPath();
        SysCmsUtils.log.info("当前请求路径: {}", requestPath);
        PathMatcher pathMatcher = new AntPathMatcher();
        // 白名单路径放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, requestPath.toString())) {
                return chain.filter(exchange);
            }
        }
        // 获取token令牌
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isNotBlank(token)) {
            token = token.substring(7);
            System.out.println("token->>>"+token);
        }else {
            return GatewayConstant.response(exchange, HttpStatus.UNAUTHORIZED, GatewayConstant.UNAUTHORIZED_TEXT, GatewayConstant.UNAUTHORIZED_JSON);
        }
        // 判断是否是有效的token
        OAuth2AccessToken oAuth2AccessToken = null;
        try{
            oAuth2AccessToken = tokenStore.readAccessToken(token);
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            String claims = MapUtils.getString(additionalInformation,"claims");
            System.out.println("claims->>>"+claims);
            //String de_claims= EncryptUtils.decryptAES_CBC(claims,token_claims_password,token_claims_ivs, EncryptUtils.EncodeType.Base64);
            String de_claims= "abc";
            ServerHttpRequest request = exchange.getRequest().mutate().header("Icc-Gateway-Authorization", de_claims).build();
            //将现在的request 变成 exchange对象
            return chain.filter(exchange.mutate().request(request).build());
        }catch (Exception e) {
            SysCmsUtils.log.info("无效的token: {}，ex:{}", token,e.getMessage());
        }
        return GatewayConstant.response(exchange,HttpStatus.UNAUTHORIZED, GatewayConstant.UNAUTHORIZED_TEXT, GatewayConstant.UNAUTHORIZED_JSON);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
