package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.result.ResultUtil;
import com.mall.order.entity.MallOrder;
import com.mall.order.mapper.MallOrderMapper;
import com.mall.order.service.MallOrderService;
import com.sku.api.feign.MallSkuFeignService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@CommonsLog
@Service
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements MallOrderService {

    @Autowired
    private MallSkuFeignService mallSkuFeignService;

    //@Transactional(rollbackFor = Exception.class)
    //阿里巴巴的Java开发者手册里面有明确规定，在 @Transactional的方法里面捕获了异常，必须要手动回滚
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public ResultUtil<?> updateOrderStatus(Integer id, Integer userId) {

        MallOrder mallOrder = this.baseMapper.selectById(id);
        log.info("1、扣减库存->>>");
        mallSkuFeignService.reduceStock(mallOrder.getGoodId());

        log.info("2、修改订单状态->>>");
        boolean result = this.update(new LambdaUpdateWrapper<MallOrder>().eq(MallOrder::getId, id).eq(MallOrder::getUserId,userId).set(MallOrder::getStatus, 1));
        if(result) {
            return ResultUtil.success("下单成功");
        }else {
            return ResultUtil.success("下单失败");
        }
    }
}
