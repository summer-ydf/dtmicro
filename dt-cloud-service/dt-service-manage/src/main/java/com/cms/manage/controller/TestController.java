package com.cms.manage.controller;

import com.cms.common.result.ResultUtil;
import com.cms.manage.service.TestProductService;
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
 * 推荐你们在开发中所有feign模块最好能统一包名前缀com.javadaily.feign
 * @author ydf Created by 2021/11/23 15:40
 */
@RestController
@RequestMapping(value = "/manage")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private TestProductService testProductService;

    @PostMapping(value = "/insert")
    public ResultUtil<?> insert() {
        return testProductService.insert();
    }

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

    @PostMapping(value = "/test")
    public ResultUtil<?> test() {
        return ResultUtil.success("返回数据");
    }
}
