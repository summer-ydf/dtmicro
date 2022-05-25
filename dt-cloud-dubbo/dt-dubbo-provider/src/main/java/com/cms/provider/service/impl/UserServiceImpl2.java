package com.cms.provider.service.impl;


import com.cms.api.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author DT辰白
 * @date 2022/5/17 20:51
 */
@DubboService(version = "2.0")
public class UserServiceImpl2 implements UserService {

    @Override
    public String getUser() {
        return "你好，Dubbo-2!";
    }
}
