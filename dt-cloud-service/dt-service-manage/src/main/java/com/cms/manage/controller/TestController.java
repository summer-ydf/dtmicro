package com.cms.manage.controller;

import com.cms.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/11/23 15:40
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/test1")
    public String test() {
        System.out.println("调用方法->>>");
        return testService.getTest();
    }
}
