package com.cms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.auth.entity.TestOrderEntity;
import com.cms.common.result.ResultUtil;

/**
 * @author ydf Created by 2021/12/22 12:42
 */
public interface TestOrderService extends IService<TestOrderEntity> {

    ResultUtil<?> createOrder(TestOrderEntity orderEntity);
}
