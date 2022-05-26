package com.cms.auth.config;


import com.alibaba.fastjson.JSON;
import com.cms.auth.config.custom.CmsTokenStore;
import com.cms.auth.config.exception.*;
import com.cms.auth.config.filter.CmsCaptchaAuthenticationFilter;
import com.cms.auth.config.filter.CmsClientCredentialsTokenEndpointFilter;
import com.cms.auth.config.filter.CmsLockAuthenticationFilter;
import com.cms.auth.config.handler.OAuth2AuthenticationFailureHandler;
import com.cms.auth.config.handler.OAuth2AuthenticationSuccessHandler;
import com.cms.auth.config.handler.RestExceptionHandler;
import com.cms.auth.config.handler.TokenAuthenticationFailureHandler;
import com.cms.auth.config.handler.TokenAuthenticationSuccessHandler;
import com.cms.auth.config.interceptor.AuthorizationInterceptor;
import com.cms.auth.service.RpcUserDetailsService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysDataScopeVoEntity;
import com.cms.common.tool.result.ResultEnum;
import com.cms.common.tool.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.*;

/**
 * OAuth2认证服务器配置
 * @author DT辰白 Created by 2021/12/14 14:46
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RpcUserDetailsService rpcUserDetailsService;

    @Resource
    private DataSource dataSource;

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 自定义异常处理类
     */
    @Autowired
    private RestExceptionHandler restExceptionHandler;

    /**
     * 认证管理器：密码模式需要
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录成功处理器
     */
    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor(oAuth2AuthenticationSuccessHandler());
    }
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new TokenAuthenticationSuccessHandler();
    }

    /**
     * 自定义异常处理
     */
    @Bean
    public OAuth2WebResponseExceptionTranslator oAuth2WebResponseExceptionTranslator() {
        return new OAuth2WebResponseExceptionTranslator(oAuth2AuthenticationFailureHandler());
    }

    /**
     * 账号密码错误处理
     */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new TokenAuthenticationFailureHandler();
    }

    /**
     * 登录账号锁定
     */
    @Bean
    public CmsLockAuthenticationFilter cmslockAuthenticationFilter() {
        return new CmsLockAuthenticationFilter();
    }

    /**
     * 验证码校验
     */
    @Bean
    public CmsCaptchaAuthenticationFilter cmsCaptchaAuthenticationFilter() {
        return new CmsCaptchaAuthenticationFilter();
    }

    /**
     * 配置客户端详情服务
     * @param clients 客户端信息
     * @throws Exception 抛出异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 采用内存方式
        clients.inMemory()
                .withClient("cms-admin-web")
                .secret(new BCryptPasswordEncoder().encode("dt$pwd123"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token");
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
                // 令牌管理服务
                .tokenServices(defaultTokenServices())
                // 配置加载用户信息的服务
                .userDetailsService(rpcUserDetailsService)
                // 自定义异常处理
                .exceptionTranslator(oAuth2WebResponseExceptionTranslator())
                // 允许端点POST提交访问令牌
                .allowedTokenEndpointRequestMethods(HttpMethod.POST).addInterceptor(authorizationInterceptor());
    }

    /**
     * 配置令牌访问端点安全的约束
     * @param oauthServer 对象
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 检测令牌
        oauthServer.tokenKeyAccess("isAuthenticated()").checkTokenAccess("permitAll()");
        // oauthServer.allowFormAuthenticationForClients();

        // 自定义client_id异常处理
        CmsClientCredentialsTokenEndpointFilter endpointFilter = new CmsClientCredentialsTokenEndpointFilter(oauthServer);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
        // 客户端认证之前的过滤器
        oauthServer.addTokenEndpointAuthenticationFilter(endpointFilter);
        oauthServer.addTokenEndpointAuthenticationFilter(cmslockAuthenticationFilter());
        // 自定义异常处理端口，访问oauth/token
        oauthServer.authenticationEntryPoint(restExceptionHandler::loginExceptionHandler);
        // 验证码校验
        oauthServer.addTokenEndpointAuthenticationFilter(cmsCaptchaAuthenticationFilter());
        oauthServer.accessDeniedHandler((req,res,ex)-> System.out.println("sss----"+ex.getMessage()));
    }

    /**
     * 配置令牌服务
     * @return 返回令牌服务信息
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices service = new DefaultTokenServices();
        // 是否刷新令牌
        service.setSupportRefreshToken(true);
        // 令牌存储策略
        service.setTokenStore(tokenStore());
        // 设置令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),jwtAccessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain);
        // token有效期设置2小时
        service.setAccessTokenValiditySeconds(60 * 60 * 2);
        // 刷新令牌设置有效期7天
        service.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return service;
    }

    /**
     * 配置token令牌存储
     */
    @Bean
    public TokenStore tokenStore() {
        return new CmsTokenStore(jwtAccessTokenConverter(),stringRedisTemplate);
    }

    /**
     * token存储处理
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 使用JWT生成token采用非对称加密方式
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        // 从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt/jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }

    /**
     * token添加额外信息：在token中携带额外的信息，这样可以在服务之间共享部分用户信息
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (OAuth2AccessToken accessToken, OAuth2Authentication authentication)-> {
            SecurityClaimsUserEntity claimsUser = (SecurityClaimsUserEntity) authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>(2);
            additionalInfo.put("userid", claimsUser.getUserid());
            additionalInfo.put("username", claimsUser.getUsername());
            additionalInfo.put("deptId", claimsUser.getDeptId());
            additionalInfo.put("isAdmin",claimsUser.isAdmin());
            // 角色数据权限
            List<SysDataScopeVoEntity> roles = claimsUser.getRoles();
            List<Map<String,Object>> resultRoles = new ArrayList<>();
            if(!roles.isEmpty()){
                for (SysDataScopeVoEntity role : roles) {
                    Map<String,Object> map = new HashMap<>(2);
                    map.put("roleId",role.getRoleId());
                    map.put("dataScope",role.getDataScope());
                    resultRoles.add(map);
                }
            }
            additionalInfo.put("roles",resultRoles);
            // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
            //additionalInfo.put("authorities", user.getAuthorities());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    /**
     * 客户端认证失败处理
     * @return 返回JSON
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.OK.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            ResultUtil<Object> error = ResultUtil.error(ResultEnum.OAUTH2_BASE_ERROR.getCode(), ResultEnum.OAUTH2_BASE_ERROR.getMessage());
            response.getWriter().print(JSON.toJSONString(error));
            response.getWriter().flush();
        };
    }

}
