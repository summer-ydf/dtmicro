package com.cms.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ydf Created by 2022/1/10 11:28
 */
@SpringBootApplication
public class CmsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsTestApplication.class,args);
        System.out.println("============================================");
        System.out.println("===============$测试服务已启动$===============");
        System.out.println("============================================");
    }
}
