package com.cms.manage.controller;

import com.api.manage.feign.ManageFeignService;
import com.cms.common.result.ResultUtil;
import com.cms.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调用方式：第一种就是我们这里介绍的，Feign和生产者的RequestMapping保持一致
 * 调用方式：第二种方式就是让我们的Controller直接实现Feign接口，不再需要写RequestMapping
 * @author ydf Created by 2021/11/23 15:40
 */
@RestController
@RequestMapping(value = "/manage")
public class TestController implements ManageFeignService {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/test1/{userId}")
    public ResultUtil<?> test(@PathVariable String userId) {
        System.out.println("调用方法->>>");
        return testService.getTest(userId);
    }

    @PostMapping(value = "/save")
    public String save() {
        System.out.println("调用方法->>>");
        return testService.save();
    }

    @Override
    public String loadUserByUsername(@RequestParam String username) {
        return "调用成功，返回用户信息："+username;
    }

    @PostMapping(value = "/test")
    public ResultUtil<?> test() {
        return ResultUtil.success("返回数据");
    }
}
