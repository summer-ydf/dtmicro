package com.cms.oauth.security.custom;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 登录成功处理器
 * @author ydf Created by 2022/5/9 17:49
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent auth) {
        System.out.println("登录成功处理================");
        System.out.println(auth.getAuthentication().getDetails());
    }
}
