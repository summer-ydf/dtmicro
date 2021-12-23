package com.cms.manage.service.impl;

import com.api.item.feign.ItemFeignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.result.ResultUtil;
import com.cms.manage.entity.TestProductEntity;
import com.cms.manage.mapper.TestProductMapper;
import com.cms.manage.service.TestProductService;
import io.seata.core.context.RootContext;
import io.seata.integration.http.XidResource;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@Service
public class TestProductServiceImpl extends ServiceImpl<TestProductMapper, TestProductEntity> implements TestProductService {

    @Autowired
    private ItemFeignService itemFeignService;

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

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public ResultUtil<?> insert() {
        TestProductEntity testProductEntity = new TestProductEntity();
        testProductEntity.setC(666);
        this.baseMapper.insert(testProductEntity);
        // 调用远程接口
        this.itemFeignService.save(666);
        return ResultUtil.success();
    }
}
