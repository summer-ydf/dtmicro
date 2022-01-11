package com.cms.auth;

import com.alibaba.fastjson.JSON;
import com.cms.auth.domain.SecurityClaimsParams;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ydf Created by 2022/1/7 17:30
 */
@SpringBootTest
@Slf4j
public class CmsAuthApplicationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createPassword() {
        String encode = passwordEncoder.encode("dt666");
        System.out.println(encode);
    }

    @Test
    public void createSecurityClaimsParams() {
        SecurityClaimsParams securityClaimsParams = new SecurityClaimsParams();
        securityClaimsParams.setScope("web");
        securityClaimsParams.setOpenid("123456789");
        String text = JSON.toJSONString(securityClaimsParams);
        System.out.println(text);
        String temp = "{\"openid\":\"123456789\",\"scope\":\"web\"}";
    }
}
