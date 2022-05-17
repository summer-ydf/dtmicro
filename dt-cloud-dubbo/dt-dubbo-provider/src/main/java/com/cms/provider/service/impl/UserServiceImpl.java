package com.cms.provider.service.impl;

import com.cms.api.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author DT
 * @date 2022/5/17 20:49
 */
@DubboService(version = "1.0")
public class UserServiceImpl implements UserService {

    @Override
    public String getUser() {
        return "你好，Dubbo-1!";
    }
}
