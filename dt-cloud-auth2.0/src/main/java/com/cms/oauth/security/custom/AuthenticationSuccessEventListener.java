package com.cms.oauth.security.custom;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 登录成功处理器
 * @author DT辰白 Created by 2022/5/9 17:49
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent auth) {
        System.out.println("登录成功处理================");
        Object details = auth.getAuthentication().getDetails();
        System.out.println(details);
        if (!ObjectUtils.isEmpty(details)) {

        }
    }
}
