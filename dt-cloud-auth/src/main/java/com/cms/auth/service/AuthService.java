package com.cms.auth.service;

import com.api.manage.feign.ManageFeignService;
import com.cms.common.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2021/12/16 11:19
 */
@Service
public class AuthService {

    @Autowired
    private ManageFeignService manageFeignService;

    public ResultUtil<String> findUserInfo(String username) {
        String userByUsername = manageFeignService.loadUserByUsername(username);
        return ResultUtil.success(userByUsername);
    }

}
