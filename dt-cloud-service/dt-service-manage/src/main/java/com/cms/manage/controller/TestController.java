package com.cms.manage.controller;

import com.cms.common.result.ResultUtil;
import com.cms.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/11/23 15:40
 */
@RestController
public class TestController {

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
}
