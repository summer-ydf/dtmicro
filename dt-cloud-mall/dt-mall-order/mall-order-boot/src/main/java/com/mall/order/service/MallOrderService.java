package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.result.ResultUtil;
import com.mall.order.entity.MallOrder;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
public interface MallOrderService extends IService<MallOrder> {

    ResultUtil<?> updateOrderStatus(Integer id, Integer userId);
}
