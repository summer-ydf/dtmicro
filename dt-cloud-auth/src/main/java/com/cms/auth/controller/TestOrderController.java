package com.cms.auth.controller;

import com.cms.auth.entity.TestOrderEntity;
import com.cms.auth.service.TestOrderService;
import com.cms.common.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/22 12:47
 */
@RestController
@RequestMapping("/order")
public class TestOrderController {

    @Autowired
    private TestOrderService testOrderService;

    /**
     * 引入Seata事务操作
     * @return
     */
    @PostMapping("/add")
    public ResultUtil<?> createOrder(@RequestBody TestOrderEntity orderEntity) {
        return testOrderService.createOrder(orderEntity);
    }
}
