package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.manage.entity.TestProductEntity;
import com.cms.manage.mapper.TestProductMapper;
import com.cms.manage.service.TestProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@Service
public class TestProductServiceImpl extends ServiceImpl<TestProductMapper, TestProductEntity> implements TestProductService {

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deductProduct(Integer a) {
        TestProductEntity productEntity = new TestProductEntity();
        productEntity.setC(a);
        this.baseMapper.insert(productEntity);
        // 远程接口抛出异常
        int n = 10/0;
        System.out.println("调用成功->>>"+a);
    }
}
