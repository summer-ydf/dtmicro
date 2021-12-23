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
 * // 参考文章：http://javadaily.cn/articles/2019/12/19/1576731515587.html
 * // https://blog.csdn.net/qq853632587/article/details/111644295
 * // https://www.cnblogs.com/haoxianrui/p/14280184.html
 * @author ydf Created by 2021/12/22 12:42
 */
@Service
public class TestOrderServiceImpl extends ServiceImpl<TestOrderMapper, TestOrderEntity> implements TestOrderService {

    @Autowired
    private ManageFeignService manageFeignService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultUtil<?> createOrder(TestOrderEntity orderEntity) {
        this.saveOrder(orderEntity);
        return ResultUtil.success();
    }

    public void saveOrder(TestOrderEntity orderEntity) {
        this.baseMapper.insert(orderEntity);
    }
}
