package com.cms.oauth.security.config;

import com.cms.common.tool.constant.ConstantSecurityCode;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysDataScopeVoEntity;
import com.cms.oauth.security.custom.CustomJwtTokenStore;
import com.cms.oauth.security.exception.OAuthWebResponseExceptionTranslator;
import com.cms.oauth.security.model.captcha.CaptchaTokenGranter;
import com.cms.oauth.security.model.idcard.IdCardTokenGranter;
import com.cms.oauth.security.model.mobile.SmsCodeTokenGranter;
import com.cms.oauth.security.model.refresh.PreAuthenticatedUserDetailsService;
import com.cms.oauth.security.model.wechat.WechatTokenGranter;
import com.cms.oauth.service.impl.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Oauth2.0认证授权配置类
 * @author DT辰白 Created by 2022/4/25 18:58
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ClientDetailsServiceImpl clientDetailsService;
    private final SysUserDetailsServiceImpl sysUserDetailsService;
    private final MemberUserDetailsServiceImpl memberUserDetailsService;
    private final WechatUserDetailsServiceImpl wechatUserDetailsService;
    private final IdCardUserDetailsServiceImpl idCardUserDetailsService;

    /**
     * 加载Oauth2客户端（数据库）
     * @param clients 客户端实例
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(clientDetailsService);
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
        // Token令牌存储策略，默认为内存存储策略，修改为Redis存储
        endpoints.tokenStore(jwtTokenStore());

        // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
        List<TokenGranter> granterList = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));

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

        // 添加身份证授权模式的授权者
        granterList.add(new IdCardTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));

        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                // 自定义异常处理
                .exceptionTranslator(new OAuthWebResponseExceptionTranslator())
                .tokenGranter(compositeTokenGranter)
                .tokenServices(tokenServices(endpoints));
    }

    /**
     * 配置token令牌存储（Redis存储）
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new CustomJwtTokenStore(jwtAccessTokenConverter(),redisTemplate);
    }

    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 是否刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // 令牌存储策略
        tokenServices.setTokenStore(endpoints.getTokenStore());
        // 客户端存储策略
        tokenServices.setClientDetailsService(clientDetailsService);
        // 设置令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>(10);
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        // 多用户体系下，刷新token再次认证客户端ID和UserDetailService的映射Map
        Map<String, UserDetailsService> clientUserDetailsServiceMap = new HashMap<>(6);
        clientUserDetailsServiceMap.put(ConstantSecurityCode.ADMIN_CLIENT_ID, sysUserDetailsService);
        clientUserDetailsServiceMap.put(ConstantSecurityCode.WEB_CLIENT_ID, sysUserDetailsService);
        clientUserDetailsServiceMap.put(ConstantSecurityCode.APP_CLIENT_ID, memberUserDetailsService);
        clientUserDetailsServiceMap.put(ConstantSecurityCode.WECHAT_CLIENT_ID, wechatUserDetailsService);
        clientUserDetailsServiceMap.put(ConstantSecurityCode.IDCARD_CLIENT_ID, idCardUserDetailsService);

        // 刷新token模式下，重写预认证提供者替换其AuthenticationManager，可自定义根据客户端ID和认证方式区分用户体系获取认证用户信息
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(clientUserDetailsServiceMap));
        tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));

        // 允许重复使用refresh token
        tokenServices.setReuseRefreshToken(true);
        return tokenServices;
    }

    /**
     * 配置令牌访问端点安全的约束
     * @param oauthServer 对象
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 检测令牌
        oauthServer.tokenKeyAccess("isAuthenticated()").checkTokenAccess("permitAll()");
        // 开启支持form表单参数提交的方式
         oauthServer.allowFormAuthenticationForClients();
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
            // 客户端登录标识
            additionalInfo.put("authenticationIdentity",claimsUser.getAuthenticationIdentity());
            // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
            //additionalInfo.put("authorities", user.getAuthorities());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}

