package com.mall.user.controller;

import com.mall.user.service.MallUserService;
import com.user.api.feign.MallUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DT辰白 Created by 2021/12/24 11:46
 */
@RestController
public class MallUserController implements MallUserFeignService {

    @Autowired
    private MallUserService mallUserService;

    @Override
    public void reduceMoney(Integer id) {
        mallUserService.reduceMoney(id);
    }
}
