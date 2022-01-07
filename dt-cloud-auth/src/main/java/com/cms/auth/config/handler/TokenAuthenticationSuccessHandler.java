package com.cms.auth.config.handler;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ydf Created by 2022/1/7 14:07
 */
public class TokenAuthenticationSuccessHandler implements OAuth2AuthenticationSuccessHandler {

//    @Autowired
//    private OlapKafkaProducer olapKafkaProducer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication authentication) throws IOException, ServletException {
        System.out.println("登录成功写入日志->>>"+authentication.getPrincipal());
//        SecurityClaimsUser securityClaimsUser= (SecurityClaimsUser) authentication.getPrincipal();
//        olapKafkaProducer.clickStreamLog(request,securityClaimsUser, "用户点击了[登录]按钮","btn-login");
//        olapKafkaProducer.loginLog(request,securityClaimsUser, JSON.toJSONString(CoreWebUtils.requestMap(request)),false);
//        System.out.println("登录成功写入日志->>>"+securityClaimsUser);
    }
}
