package com.cms.gateway.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.cms.common.tool.result.ResultEnum;
import com.cms.gateway.GatewayConstant;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

/**
 * 资源服务器配置类
 * @author ydf Created by 2022/4/26 10:16
 */
@Slf4j
@ConfigurationProperties(prefix = "security")
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private final ResourceServerManager resourceServerManager;

    @Setter
    private List<String> ignoreUrls;

    public ResourceServerConfig(ResourceServerManager resourceServerManager) {
        this.resourceServerManager = resourceServerManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
                // 本地获取公钥
                .publicKey(rsaPublicKey());
               //.jwkSetUri() // 远程获取公钥
        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
        http.authorizeExchange()
                // 白名单不拦截
                .pathMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
                // 配置鉴权管理器
                .anyExchange().access(resourceServerManager)
                .and()
                .exceptionHandling()
                // 处理未授权
                .accessDeniedHandler(accessDeniedHandler())
                // 处理未认证
                .authenticationEntryPoint(authenticationEntryPoint())
                .and().csrf().disable();

        return http.build();
    }

    /**
     * 未授权自定义响应
     */
    @Bean
    public ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> {
            log.info("未授权自定义响应");
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> GatewayConstant.responseErrorJson(exchange, ResultEnum.ACCESS_UNAUTHORIZED));
            return mono;
        };
    }

    /**
     * token无效或者已过期自定义响应
     */
    @Bean
    public ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, e) -> {
            log.info("token无效或者已过期自定义响应");
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> GatewayConstant.responseErrorJson(exchange, ResultEnum.TOKEN_INVALID_OR_EXPIRED));
            return mono;
        };
    }

    /**
     * @return
     * @link https://blog.csdn.net/qq_24230139/article/details/105091273
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // JWT存储权限前缀
        //jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        // JWT存储权限属性
        //jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    /**
     * 本地加载JWT验签公钥
     * @return 返回公钥
     */
    @SneakyThrows
    @Bean
    public RSAPublicKey rsaPublicKey() {
        Resource resource = new ClassPathResource("jwt/public.key");
        InputStream is = resource.getInputStream();
        String publicKeyData = IoUtil.read(is).toString();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec((Base64.decode(publicKeyData)));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey)keyFactory.generatePublic(keySpec);
    }
}

