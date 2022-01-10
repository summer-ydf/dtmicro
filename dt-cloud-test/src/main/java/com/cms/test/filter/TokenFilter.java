package com.cms.test.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Arrays;

/**
 * @author ydf Created by 2022/1/7 17:52
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {

//    @Autowired
//    private IgnoreUrlsConfig ignoreUrlsConfig;

//        @Autowired
//    private TokenStore tokenStore;

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    public static boolean urlMatch(String[] urls, String requestPath) {
        return StringUtils.isNotBlank(requestPath) && Arrays.stream(urls).anyMatch(url->PATH_MATCHER.match(url, requestPath));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isEmpty(token)) {
            System.out.println("token不存在->>>");
            return chain.filter(exchange);
        }

        //3 判断是否是有效的token
//        OAuth2AccessToken oAuth2AccessToken = null;
//        try{
//            oAuth2AccessToken = tokenStore.readAccessToken(token);
//            if(oAuth2AccessToken==null) {
//                return GatewayConstant.response(exchange, HttpStatus.UNAUTHORIZED, GatewayConstant.UNAUTHORIZED_TEXT, GatewayConstant.UNAUTHORIZED_JSON);
//            }
//            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
//            String claims = MapUtils.getString(additionalInformation,"claims");
//            String de_claims= EncryptUtils.decryptAES_CBC(claims,token_claims_password,token_claims_ivs, EncryptUtils.EncodeType.Base64);
//            ServerHttpRequest request = exchange.getRequest().mutate().header(token_gateway_header_name, de_claims).build();
//            //将现在的request 变成 exchange对象
//            return chain.filter(exchange.mutate().request(request).build());
//        }catch (Exception e){
//            log.info("无效的token: {}，ex:{}", token,e.getMessage());
//        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

//

//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        RequestPath requestPath =exchange.getRequest().getPath();
//        if(urlMatch(ignoreUrlsConfig.filterAllUrls(),requestPath.toString())){
//            return chain.filter(exchange);
//        }
//        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if(StringUtils.isNotBlank(token)){
//            token=token.substring(7);
//        }else {
//            MultiValueMap multiValueMap=exchange.getRequest().getQueryParams();
//            token= ObjectUtils.toString(multiValueMap.getFirst("access_token"));
//        }
//        if(StringUtils.isEmpty(token)){
//            return GatewayConstant.response(exchange,HttpStatus.UNAUTHORIZED, GatewayConstant.UNAUTHORIZED_TEXT, GatewayConstant.UNAUTHORIZED_JSON);
//        }
//        //3 判断是否是有效的token
//        OAuth2AccessToken oAuth2AccessToken=null;
//        try{
//            oAuth2AccessToken=tokenStore.readAccessToken(token);
//            if(oAuth2AccessToken==null){
//                return GatewayConstant.response(exchange, HttpStatus.UNAUTHORIZED, GatewayConstant.UNAUTHORIZED_TEXT, GatewayConstant.UNAUTHORIZED_JSON);
//            }
//            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
//            String claims = MapUtils.getString(additionalInformation,"claims");
//            String de_claims= EncryptUtils.decryptAES_CBC(claims,token_claims_password,token_claims_ivs, EncryptUtils.EncodeType.Base64);
//            ServerHttpRequest request = exchange.getRequest().mutate().header(token_gateway_header_name, de_claims).build();
//            //将现在的request 变成 exchange对象
//            return chain.filter(exchange.mutate().request(request).build());
//        }catch (Exception e){
//            log.info("无效的token: {}，ex:{}", token,e.getMessage());
//        }
//        return GatewayConstant.response(exchange,HttpStatus.UNAUTHORIZED, GatewayConstant.UNAUTHORIZED_TEXT, GatewayConstant.UNAUTHORIZED_JSON);
//    }
//    @Override
//    public int getOrder() {
//        return 10;
//    }




}
