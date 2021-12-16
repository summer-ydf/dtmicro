package com.cms.auth.config;

import com.cms.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务器配置
 * @author ydf Created by 2021/12/14 14:46
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    /**
     * 客户端详情服务
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 令牌策略
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * 认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 授权码模式
     */
    private AuthorizationCodeServices authenticationCodeService;

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        if (authenticationCodeService == null) {
            authenticationCodeService = new InMemoryAuthorizationCodeServices();
        }
        return authenticationCodeService;
    }

    /**
     * 配置客户端详情服务
     * 参考：https://github.com/hxrui/youlai-mall
     * 申请授权码：http://localhost:9401/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_uri=http://www.baidu.com
     * @param clients 客户端信息
     * @throws Exception 异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //TODO 暂时使用内存的方式配置
        clients.inMemory()
                // 客户端的ID
                .withClient("c1")
                // 客户端的秘钥
                .secret(passwordEncoder.encode("secret"))
                // 允许访问的资源列表
                .resourceIds("res1")
                // 允许给客户端授权类型，一共五种
                .authorizedGrantTypes("authorization_code","password","refresh_token","implicit","client_credentials")
                // 允许的授权范围(读或者写)
                .scopes("all")
                // 自动授权配置，false跳转到授权页面，true直接发送令牌
                .autoApprove(false)
                // 验证回调地址
                .redirectUris("http://www.baidu.com");
    }

    /**
     * 用来配置令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints 返回
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                // 授权码模式需要
                .authorizationCodeServices(authenticationCodeService)
                // 令牌管理服务
                .tokenServices(tokenServices())
                // 允许端点POST提交访问令牌
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);

    }

    /**
     * 配置令牌访问端点安全的约束
     * @param security 返回
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 提供公钥端点
                .tokenKeyAccess("permitAll()")
                // 检测令牌
                .checkTokenAccess("permitAll()")
                // 允许表单认证
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置令牌服务
     * @return 返回令牌服务信息
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        // 客户端信息服务
        services.setClientDetailsService(clientDetailsService);
        // 是否刷新令牌
        services.setSupportRefreshToken(true);
        // 令牌存储策略
        services.setTokenStore(tokenStore);
        // 设置令牌增强
        //TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        //tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(jwtAccessTokenConverter));
        //services.setTokenEnhancer(tokenEnhancerChain);
        // 令牌默认有效期2天
        services.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有效期3天
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }
}
