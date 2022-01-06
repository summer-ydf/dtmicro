package com.cms.auth.controller;

import com.cms.common.result.ResultUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/16 11:19
 */
@RestController
public class AuthController {

    @GetMapping("/hello")
    public String hello() {
        return "hello!!!!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String admin() {
        return "admin!!!!";
    }

    @GetMapping(value = "/findUserInfo/{username}")
    public ResultUtil<String> findUserInfo(@PathVariable String username) {
        return ResultUtil.success(username);
    }
}
