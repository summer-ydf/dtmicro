package com.cms.auth.config;


import com.cms.auth.config.exception.OAuth2AuthenticationFailureHandler;
import com.cms.auth.config.exception.OAuth2WebResponseExceptionTranslator;
import com.cms.auth.config.exception.RestExceptionHandler;
import com.cms.auth.config.exception.TokenAuthenticationFailureHandler;
import com.cms.auth.config.filter.IccCaptchaAuthenticationFilter;
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

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 认证服务器配置
 * @author ydf Created by 2021/12/14 14:46
 */
@Configuration
@EnableAuthorizationServer  // 开启认证服务
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private UserService userService;

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

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    /**
     * 认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 授权码模式
     */
//    @Autowired
//    private AuthorizationCodeServices authenticationCodeService;

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        // 设置授权码模式的授权码如何 存取，这里采用内存方式
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 自定义异常处理
     * @return
     */
    @Bean
    public OAuth2WebResponseExceptionTranslator oAuth2WebResponseExceptionTranslator() {
        return new OAuth2WebResponseExceptionTranslator(oAuth2AuthenticationFailureHandler());
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new TokenAuthenticationFailureHandler();
    }

    /**
     * 验证码校验
     * @return
     */
    @Bean
    public IccCaptchaAuthenticationFilter iccCaptchaAuthenticationFilter() {
        return new IccCaptchaAuthenticationFilter();
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
                .withClient("myjszl")
                // 客户端的秘钥
                .secret(passwordEncoder.encode("123"))
                // 允许访问的资源列表
                //.resourceIds("res1")
                // 允许给客户端授权类型，一共五种
                .authorizedGrantTypes("password","refresh_token")
                // 允许的授权范围(读或者写)
                .scopes("all");
                // 自动授权配置，false跳转到授权页面，true直接发送令牌
                //.autoApprove(false)
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
                .authorizationCodeServices(authorizationCodeServices())
                // 令牌管理服务
                .tokenServices(tokenServices())
                // 允许端点POST提交访问令牌
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                // 自定义异常处理
                .exceptionTranslator(oAuth2WebResponseExceptionTranslator());
    }

    /**
     * 配置令牌访问端点安全的约束
     * @param oauthServer 返回
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("isAuthenticated()").checkTokenAccess("isAuthenticated()");

        // 自定义异常处理端口，访问oauth/token时，当信息不全将拒绝,这里主要是client_id和secret的验证,无论是认证服务还是资源服务都在这里处理
        oauthServer.authenticationEntryPoint(restExceptionHandler::loginExceptionHandler);
        // 客户端认证之前的过滤器
        //security.addTokenEndpointAuthenticationFilter(iccLoginClaimsFilter());
        // 验证码校验
        oauthServer.addTokenEndpointAuthenticationFilter(iccCaptchaAuthenticationFilter());
        //security.addTokenEndpointAuthenticationFilter(iccLockAuthenticationFilter());
        oauthServer.allowFormAuthenticationForClients();
        oauthServer.accessDeniedHandler((req,res,ex)-> {
            System.out.println("sss----"+ex.getMessage());
        });

//        security
//                // 提供公钥端点
//                .tokenKeyAccess("permitAll()")
//                // 检测令牌
//                .checkTokenAccess("isAuthenticated()")
//                // 支持client_id和client_secret做登录认证
//                .allowFormAuthenticationForClients()
//                .addTokenEndpointAuthenticationFilter(new JiheBasicAuthenticationFilter());
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
