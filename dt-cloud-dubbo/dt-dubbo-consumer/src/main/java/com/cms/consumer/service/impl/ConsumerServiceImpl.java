package com.cms.consumer.service.impl;

import com.cms.api.UserService;
import com.cms.consumer.service.ConsumerService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author DT
 * @date 2022/5/17 20:43
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @DubboReference(version = "1.0") // 表示引入一个服务,也可以指定版本
    private UserService userService;

    @Override
    public String getInfo() {
        return userService.getUser();
    }
}
