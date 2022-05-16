package com.cms.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DT
 * @date 2022/5/16 22:55
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/myLogin")
    public String login() {
        // https://www.cnblogs.com/lzj123/p/10279007.html
        System.out.println("返回自定义登录页面");
        return "login";
    }
}
