package com.cms.auth.controller;

import com.cms.auth.service.AuthService;
import com.cms.common.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/16 11:19
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping(value = "/findUserInfo/{username}")
    public ResultUtil<String> findUserInfo(@PathVariable String username) {
        return authService.findUserInfo(username);
    }
}
