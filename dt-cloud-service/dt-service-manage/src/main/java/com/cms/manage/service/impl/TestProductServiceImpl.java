package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.manage.entity.TestProductEntity;
import com.cms.manage.mapper.TestProductMapper;
import com.cms.manage.service.TestProductService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@Service
public class TestProductServiceImpl extends ServiceImpl<TestProductMapper, TestProductEntity> implements TestProductService {

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deductProduct(Integer a) {
        TestProductEntity productEntity = new TestProductEntity();
        productEntity.setC(a);
        this.baseMapper.insert(productEntity);
        // 远程接口抛出异常
        int n = 10/0;
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("调用成功->>>"+a);
    }
}
