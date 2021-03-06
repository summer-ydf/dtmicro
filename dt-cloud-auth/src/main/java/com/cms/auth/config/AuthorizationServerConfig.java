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
 * OAuth2?????????????????????
 * @author DT?????? Created by 2021/12/14 14:46
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
     * ????????????????????????
     */
    @Autowired
    private RestExceptionHandler restExceptionHandler;

    /**
     * ????????????????????????????????????
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * ?????????????????????
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
     * ?????????????????????
     */
    @Bean
    public OAuth2WebResponseExceptionTranslator oAuth2WebResponseExceptionTranslator() {
        return new OAuth2WebResponseExceptionTranslator(oAuth2AuthenticationFailureHandler());
    }

    /**
     * ????????????????????????
     */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new TokenAuthenticationFailureHandler();
    }

    /**
     * ??????????????????
     */
    @Bean
    public CmsLockAuthenticationFilter cmslockAuthenticationFilter() {
        return new CmsLockAuthenticationFilter();
    }

    /**
     * ???????????????
     */
    @Bean
    public CmsCaptchaAuthenticationFilter cmsCaptchaAuthenticationFilter() {
        return new CmsCaptchaAuthenticationFilter();
    }

    /**
     * ???????????????????????????
     * @param clients ???????????????
     * @throws Exception ????????????
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // ??????????????????
        clients.inMemory()
                .withClient("cms-admin-web")
                .secret(new BCryptPasswordEncoder().encode("dt$pwd123"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token");
    }

    /**
     * ?????????????????????token?????????????????????????????????(token services)
     * @param endpoints ??????
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // ??????????????????
                .authenticationManager(authenticationManager)
                // ??????????????????
                .tokenServices(defaultTokenServices())
                // ?????????????????????????????????
                .userDetailsService(rpcUserDetailsService)
                // ?????????????????????
                .exceptionTranslator(oAuth2WebResponseExceptionTranslator())
                // ????????????POST??????????????????
                .allowedTokenEndpointRequestMethods(HttpMethod.POST).addInterceptor(authorizationInterceptor());
    }

    /**
     * ???????????????????????????????????????
     * @param oauthServer ??????
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // ????????????
        oauthServer.tokenKeyAccess("isAuthenticated()").checkTokenAccess("permitAll()");
        // oauthServer.allowFormAuthenticationForClients();

        // ?????????client_id????????????
        CmsClientCredentialsTokenEndpointFilter endpointFilter = new CmsClientCredentialsTokenEndpointFilter(oauthServer);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
        // ?????????????????????????????????
        oauthServer.addTokenEndpointAuthenticationFilter(endpointFilter);
        oauthServer.addTokenEndpointAuthenticationFilter(cmslockAuthenticationFilter());
        // ????????????????????????????????????oauth/token
        oauthServer.authenticationEntryPoint(restExceptionHandler::loginExceptionHandler);
        // ???????????????
        oauthServer.addTokenEndpointAuthenticationFilter(cmsCaptchaAuthenticationFilter());
        oauthServer.accessDeniedHandler((req,res,ex)-> System.out.println("sss----"+ex.getMessage()));
    }

    /**
     * ??????????????????
     * @return ????????????????????????
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices service = new DefaultTokenServices();
        // ??????????????????
        service.setSupportRefreshToken(true);
        // ??????????????????
        service.setTokenStore(tokenStore());
        // ??????????????????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),jwtAccessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain);
        // token???????????????2??????
        service.setAccessTokenValiditySeconds(60 * 60 * 2);
        // ???????????????????????????7???
        service.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return service;
    }

    /**
     * ??????token????????????
     */
    @Bean
    public TokenStore tokenStore() {
        return new CmsTokenStore(jwtAccessTokenConverter(),stringRedisTemplate);
    }

    /**
     * token????????????
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // ??????JWT??????token???????????????????????????
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        // ???classpath??????????????????????????????
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt/jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }

    /**
     * token????????????????????????token??????????????????????????????????????????????????????????????????????????????
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
            // ??????????????????
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
            // ??????????????????????????????????????????????????????json????????????key???????????????????????????
            //additionalInfo.put("authorities", user.getAuthorities());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    /**
     * ???????????????????????????
     * @return ??????JSON
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
