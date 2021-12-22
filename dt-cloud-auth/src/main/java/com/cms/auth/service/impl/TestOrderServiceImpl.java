package com.cms.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.auth.entity.TestOrderEntity;
import com.cms.auth.mapper.TestOrderMapper;
import com.cms.auth.service.TestOrderService;
import com.cms.common.result.ResultUtil;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2021/12/22 12:42
 */
@Service
public class TestOrderServiceImpl extends ServiceImpl<TestOrderMapper, TestOrderEntity> implements TestOrderService {

    @Override
    public ResultUtil<?> createOrder(TestOrderEntity orderEntity) {
        this.baseMapper.insert(orderEntity);
        return ResultUtil.success();
    }
}
