package com.cms.auth.service.impl;

import com.api.manage.feign.ManageFeignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.auth.entity.TestOrderEntity;
import com.cms.auth.mapper.TestOrderMapper;
import com.cms.auth.service.TestOrderService;
import com.cms.common.result.ResultUtil;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    //@Transactional(rollbackFor = RuntimeException.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public ResultUtil<?> createOrder(TestOrderEntity orderEntity) {
        // 调用本地接口 (主函数入口，必须添加：@Transactional注解)
        this.saveOrder(orderEntity);
        System.out.println("ORDER XID is: " + RootContext.getXID());
        // 调用远程接口
        // 使用Seata实现了分布式事务，保证了数据的一致性 只需在主函数出添加 @GlobalTransactional
        manageFeignService.deductProduct(orderEntity.getA());
        // 本地抛异常
        //int a = 10 / 0;
        return ResultUtil.success();
    }

    public void saveOrder(TestOrderEntity orderEntity) {
        this.baseMapper.insert(orderEntity);
    }
}
