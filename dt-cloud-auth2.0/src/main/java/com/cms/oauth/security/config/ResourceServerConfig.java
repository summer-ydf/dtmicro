package com.cms.oauth.security.config;


/**
 * OAuth2资源服务器配置
 * @author ydf Created by 2021/12/14 14:56
 */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        // 设置白名单不拦截
////        for (String url : ignoreUrlsConfig().getUrls()) {
////            http.authorizeRequests().antMatchers(url).permitAll();
////        }
//        http.authorizeRequests().antMatchers("/oauth/**","/hello","/anonymous/**").permitAll()
//                // @link https://gitee.com/xiaoym/knife4j/issues/I1Q5X6 (接口文档knife4j需要放行的规则)
//                .antMatchers("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs").permitAll()
//                .anyRequest().authenticated();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//}