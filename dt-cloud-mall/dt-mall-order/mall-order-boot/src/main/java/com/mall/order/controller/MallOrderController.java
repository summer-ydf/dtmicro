package com.mall.order.controller;

import com.cms.common.tool.result.ResultUtil;
import com.mall.order.service.MallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/24 10:38
 */
@RestController
@RequestMapping(value = "mall-order")
public class MallOrderController {

    @Autowired
    private MallOrderService mallOrderService;

    /**
     * 模拟用户下单操作
     * @param id 订单ID
     * @param userId 用户ID
     * @return 返回
     */
    @GetMapping(value = "updateOrder/{id}/{userId}")
    public ResultUtil<?> updateOrderStatus(@PathVariable Integer id, @PathVariable Integer userId) {
        return mallOrderService.updateOrderStatus(id,userId);
    }

    /**
     * 模拟本地事务失效
     */
    @GetMapping(value = "/updateTest/{id}")
    public ResultUtil<?> updateTest(@PathVariable Integer id) {
        return mallOrderService.updateTest(id);
    }
}
