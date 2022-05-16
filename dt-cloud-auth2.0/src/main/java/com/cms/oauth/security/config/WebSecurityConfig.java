package com.cms.oauth.security.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.api.manage.feign.OauthFeignClientService;
import com.cms.oauth.security.model.idcard.IdCardAuthenticationProvider;
import com.cms.oauth.security.model.mobile.SmsCodeAuthenticationProvider;
import com.cms.oauth.security.model.wechat.WechatAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security安全拦截配置
 * @author ydf Created by 2022/4/25 18:58
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService sysUserDetailsService;
    private final UserDetailsService memberUserDetailsService;
    private final UserDetailsService wechatUserDetailsService;
    private final UserDetailsService idCardUserDetailsService;
    private final WxMaService wxMaService;
    private final OauthFeignClientService oauthFeignClientService;
    private final RedisTemplate<String, Object> redisTemplate;

    public WebSecurityConfig(UserDetailsService sysUserDetailsService, UserDetailsService memberUserDetailsService, UserDetailsService wechatUserDetailsService, UserDetailsService idCardUserDetailsService, WxMaService wxMaService, OauthFeignClientService oauthFeignClientService, RedisTemplate<String, Object> redisTemplate) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.memberUserDetailsService = memberUserDetailsService;
        this.wechatUserDetailsService = wechatUserDetailsService;
        this.idCardUserDetailsService = idCardUserDetailsService;
        this.wxMaService = wxMaService;
        this.oauthFeignClientService = oauthFeignClientService;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 设置白名单不拦截
        for (String url : ignoreUrlsConfig().getUrls()) {
            http.authorizeRequests().antMatchers(url).permitAll();
        }
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/myLogin") // 配置自定义登录页面（授权码模式使用）
                .and()
                .csrf()
                .disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(smsCodeAuthenticationProvider())
                .authenticationProvider(wechatAuthenticationProvider())
                .authenticationProvider(idCardAuthenticationProvider());
    }

    /**
     * 手机验证码认证授权提供者
     */
    @Bean
    public SmsCodeAuthenticationProvider smsCodeAuthenticationProvider() {
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(memberUserDetailsService);
        provider.setRedisTemplate(redisTemplate);
        return provider;
    }

    /**
     * 微信认证授权提供者
     */
    @Bean
    public WechatAuthenticationProvider wechatAuthenticationProvider() {
        WechatAuthenticationProvider provider = new WechatAuthenticationProvider();
        provider.setUserDetailsService(wechatUserDetailsService);
        provider.setWxMaService(wxMaService);
        provider.setOauthFeignClientService(oauthFeignClientService);
        return provider;
    }

    /**
     * 用户名密码认证授权提供者
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sysUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        // 是否隐藏用户不存在异常，默认:true 隐藏；false 抛出异常；
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    /**
     * 身份证认证授权提供者
     */
    @Bean
    public IdCardAuthenticationProvider idCardAuthenticationProvider() {
        IdCardAuthenticationProvider provider = new IdCardAuthenticationProvider();
        provider.setUserDetailsService(idCardUserDetailsService);
        return provider;
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
