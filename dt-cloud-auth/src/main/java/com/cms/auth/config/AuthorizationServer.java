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
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 认证服务器配置
 * @author ydf Created by 2021/12/14 14:46
 */
@Configuration
@EnableAuthorizationServer  // 开启认证服务
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Resource
    private DataSource dataSource;

    /**
     * 客户端详情服务
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

//    @Bean
//    public ClientDetailsService clientDetails() {
//        return new JdbcClientDetailsService(dataSource);
//    }

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
    @Autowired
    private AuthorizationCodeServices authenticationCodeService;

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        // 设置授权码模式的授权码如何 存取，这里采用内存方式
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 配置客户端详情服务
     * 参考：https://github.com/hxrui/youlai-mall
     * 申请授权码：http://localhost:8083/oauth/authorize?client_id=client&response_type=code&scope=app&redirect_uri=http://www.baidu.com
     * http://localhost:8083/oauth/token?client_id=client&client_secret=secret&grant_type=authorization_code&code&redirect_url=http://www.baidu.com
     * http://localhost:8083/oauth/authorize?client_id=client&response_type=code
     * 登录成功之后：获取授权码请求地址：http://client:secret@localhost:8083/oauth/token
     * @param clients 客户端信息
     * @throws Exception 异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //TODO 暂时使用内存的方式配置
        clients.inMemory()
                // 客户端的ID
                .withClient("client")
                // 客户端的秘钥
                .secret(passwordEncoder.encode("secret"))
                // 允许访问的资源列表
                //.resourceIds("res1")
                // 允许给客户端授权类型，一共五种
                .authorizedGrantTypes("authorization_code","password","refresh_token","implicit","client_credentials")
                // 允许的授权范围(读或者写)
                .scopes("app");
                // 自动授权配置，false跳转到授权页面，true直接发送令牌
                //.autoApprove(false);
                // 验证回调地址 授权地址：localhost:8083/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
                //.redirectUris("http://www.baidu.com");
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
                .checkTokenAccess("isAuthenticated()")
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
