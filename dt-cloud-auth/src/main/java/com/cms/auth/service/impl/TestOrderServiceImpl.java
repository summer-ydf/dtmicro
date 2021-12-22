package com.cms.auth.service.impl;

import com.api.manage.feign.ManageFeignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.auth.entity.TestOrderEntity;
import com.cms.auth.mapper.TestOrderMapper;
import com.cms.auth.service.TestOrderService;
import com.cms.common.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ydf Created by 2021/12/22 12:42
 */
@Service
public class TestOrderServiceImpl extends ServiceImpl<TestOrderMapper, TestOrderEntity> implements TestOrderService {

    @Autowired
    private ManageFeignService manageFeignService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultUtil<?> createOrder(TestOrderEntity orderEntity) {
        // 调用本地接口 (主函数入口，必须添加：@Transactional注解)
        this.saveOrder(orderEntity);
        // 调用远程接口
        manageFeignService.deductProduct(orderEntity.getA());
        // 本地抛异常
        int a = 10 / 0;
        return ResultUtil.success();
    }

    public void saveOrder(TestOrderEntity orderEntity) {
        this.baseMapper.insert(orderEntity);
    }
}
