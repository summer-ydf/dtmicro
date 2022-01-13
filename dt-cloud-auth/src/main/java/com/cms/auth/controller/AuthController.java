package com.cms.auth.controller;

import com.cms.common.result.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * // 第三方登录，参考：https://www.cnblogs.com/haoxianrui/
 * @author ydf Created by 2021/12/16 11:19
 */
@RestController
public class AuthController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("不需要校验======================");
        return "hello!!!!";
    }

    @GetMapping("/admin")
    public String admin() {
        System.out.println("获取资源===============");
        return "admin!!!!";
    }

    @GetMapping(value = "/findUserInfo/{username}")
    public ResultUtil<String> findUserInfo(@PathVariable String username) {
        return ResultUtil.success(username);
    }
}
