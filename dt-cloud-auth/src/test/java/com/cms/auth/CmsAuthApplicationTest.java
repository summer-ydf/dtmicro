package com.cms.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author DT辰白 Created by 2022/1/7 17:30
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

}
