package com.cms.oauth.security.config;

import com.alibaba.fastjson.JSON;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysDataScopeVoEntity;
import com.cms.common.tool.result.ResultEnum;
import com.cms.common.tool.result.ResultUtil;
import com.cms.oauth.security.exception.OAuth2WebResponseExceptionTranslator;
import com.cms.oauth.security.handler.OAuth2AuthenticationFailureHandler;
import com.cms.oauth.security.handler.OAuth2AuthenticationSuccessHandler;
import com.cms.oauth.security.handler.RestExceptionHandler;
import com.cms.oauth.security.handler.TokenAuthenticationFailureHandler;
import com.cms.oauth.security.handler.TokenAuthenticationSuccessHandler;
import com.cms.oauth.security.interceptor.AuthorizationInterceptor;
import com.cms.oauth.security.model.captcha.CaptchaTokenGranter;
import com.cms.oauth.security.model.mobile.SmsCodeTokenGranter;
import com.cms.oauth.security.model.wechat.WechatTokenGranter;
import com.cms.oauth.service.impl.SysUserDetailsServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Oauth2.0认证授权配置
 * @author DT
 * @date 2022/4/25 18:58
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserDetailsServiceImpl userDetailsService;
//    @Autowired
//    private ClientDetailsServiceImpl clientDetailsService;

    /**
     * 自定义异常处理类
     */
    @Autowired
    private RestExceptionHandler restExceptionHandler;

    /**
     * 自定义异常处理
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
     * OAuth2客户端【数据库加载】
     */
//    @Override
//    @SneakyThrows
//    public void configure(ClientDetailsServiceConfigurer clients) {
//        clients.withClientDetails(clientDetailsService);
//    }

    /**
     * OAuth2客户端【内存加载】
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.inMemory()
                // 客户端ID
                .withClient("cms")
                // 客户端密钥
                .secret(new BCryptPasswordEncoder().encode("dt$pwd123"))
                // 授权范围域
                .scopes("all")
                // 授权方式
                .authorizedGrantTypes("password", "refresh_token","captcha","sms_code","wechat");
    }

    /**
     * 用来配置令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints 返回
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // Token增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        //token存储模式设定 默认为InMemoryTokenStore模式存储到内存中
        //endpoints.tokenStore(jwtTokenStore());

        // TODO 验证码模式改造
        // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
        List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));

        // 添加验证码授权模式授权者
        granterList.add(new CaptchaTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager, redisTemplate
        ));

        // 添加手机短信验证码授权模式的授权者
        granterList.add(new SmsCodeTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));

        // 添加微信授权模式的授权者
        granterList.add(new WechatTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));

        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                // 配置加载用户信息的服务
                .userDetailsService(userDetailsService)
                // 自定义异常处理
                .exceptionTranslator(oAuth2WebResponseExceptionTranslator())
                //.userDetailsService(userDetailsService)
                // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                //      1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //      2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
                .tokenGranter(compositeTokenGranter)
                .reuseRefreshTokens(true)
                // 允许端点POST提交访问令牌
                .allowedTokenEndpointRequestMethods(HttpMethod.POST).addInterceptor(authorizationInterceptor());
//        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);
//        endpoints
//                .authenticationManager(authenticationManager)
//                .accessTokenConverter(jwtAccessTokenConverter())
//                .tokenEnhancer(tokenEnhancerChain)
//                .tokenGranter(compositeTokenGranter)
//
//                .tokenServices(tokenServices(endpoints))
//        ;
    }

//    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
//        tokenEnhancers.add(tokenEnhancer());
//        tokenEnhancers.add(jwtAccessTokenConverter());
//        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
//
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setClientDetailsService(clientDetailsService);
//        tokenServices.setTokenEnhancer(tokenEnhancerChain);
//
//        // 多用户体系下，刷新token再次认证客户端ID和 UserDetailService 的映射Map
//        Map<String, UserDetailsService> clientUserDetailsServiceMap = new HashMap<>();
//        clientUserDetailsServiceMap.put(SecurityConstants.ADMIN_CLIENT_ID, sysUserDetailsService); // 系统管理客户端
//        clientUserDetailsServiceMap.put(SecurityConstants.APP_CLIENT_ID, memberUserDetailsService); // Android、IOS、H5 移动客户端
//        clientUserDetailsServiceMap.put(SecurityConstants.WEAPP_CLIENT_ID, memberUserDetailsService); // 微信小程序客户端
//
//        // 刷新token模式下，重写预认证提供者替换其AuthenticationManager，可自定义根据客户端ID和认证方式区分用户体系获取认证用户信息
//        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(clientUserDetailsServiceMap));
//        tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
//
//        /** refresh_token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
//         *  1 重复使用：access_token过期刷新时， refresh_token过期时间未改变，仍以初次生成的时间为准
//         *  2 非重复使用：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新便永不失效达到无需再次登录的目的
//         */
//        tokenServices.setReuseRefreshToken(true);
//        return tokenServices;
//
//    }


    /**
     * 配置令牌访问端点安全的约束
     * @param oauthServer 对象
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 检测令牌
        oauthServer.tokenKeyAccess("isAuthenticated()").checkTokenAccess("permitAll()");
        // 自定义client_id认证失败处理
//        CmsClientCredentialsTokenEndpointFilter endpointFilter = new CmsClientCredentialsTokenEndpointFilter(oauthServer);
//        endpointFilter.afterPropertiesSet();
//        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
//        // 客户端认证之前的过滤器
//        oauthServer.addTokenEndpointAuthenticationFilter(endpointFilter);
//        oauthServer.authenticationEntryPoint(authenticationEntryPoint());
        // 开启支持form表单参数提交的方式
         oauthServer.allowFormAuthenticationForClients();
        // 自定义异常处理端口，访问oauth/token
        //oauthServer.authenticationEntryPoint(restExceptionHandler::loginExceptionHandler);
//        oauthServer.accessDeniedHandler((req,res,ex)-> System.out.println("sss----"+ex.getMessage()));
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        // 从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt/jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }

    /**
     * JWT内容增强
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

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setHideUserNotFoundExceptions(false); // 用户不存在异常抛出
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

    /**
     * 密码编码器
     * 委托方式，根据密码的前缀选择对应的encoder，例如：{bcypt}前缀->标识BCYPT算法加密；{noop}->标识不使用任何加密即明文的方式
     * 密码判读 DaoAuthenticationProvider#additionalAuthenticationChecks
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }


    /**
     * 客户端认证失败处理
     * @return 返回JSON
     */
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return (request, response, e) -> {
//            System.out.println("客户端认证失败处理================================================");
//            response.setStatus(HttpStatus.OK.value());
//            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
//            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
//            ResultUtil<Object> error = ResultUtil.error(ResultEnum.OAUTH2_BASE_ERROR.getCode(), ResultEnum.OAUTH2_BASE_ERROR.getMessage());
//            response.getWriter().print(JSON.toJSONString(error));
//            response.getWriter().flush();
//        };
//    }
}

