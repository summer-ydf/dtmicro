package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.manage.entity.TestProductEntity;
import com.cms.manage.mapper.TestProductMapper;
import com.cms.manage.service.TestProductService;
import io.seata.core.context.RootContext;
import io.seata.integration.http.XidResource;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@Service
public class TestProductServiceImpl extends ServiceImpl<TestProductMapper, TestProductEntity> implements TestProductService {

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deductProduct(Integer a, String xid) {
        // 绑定
        RootContext.bind(xid);
        System.out.println("xid is ->>"+xid);
        TestProductEntity productEntity = new TestProductEntity();
        productEntity.setC(a);
        this.baseMapper.insert(productEntity);
        System.out.println("ORDER XID is: " + RootContext.getXID());
        // 远程接口抛出异常
        int n = 10/0;


        // 清空资源
        XidResource.cleanXid(xid);
        System.out.println("调用成功->>>"+a);
    }
}
