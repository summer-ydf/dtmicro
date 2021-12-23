package com.cms.manage.controller;

import com.api.manage.feign.ManageFeignService;
import com.cms.manage.service.TestProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/22 13:51
 */
@RestController
public class TestProductController implements ManageFeignService {

    @Autowired
    private TestProductService testProductService;

    @Override
    public String loadUserByUsername(String username) {
        return "调用成功，返回用户信息："+username;
    }

    @Override
    public void deductProduct(Integer a, String xid) {
        testProductService.deductProduct(a,xid);
    }
}
