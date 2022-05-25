package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.result.ResultUtil;
import com.mall.order.entity.MallOrder;
import com.mall.order.mapper.MallOrderMapper;
import com.mall.order.service.MallOrderService;
import com.sku.api.feign.MallSkuFeignService;
import com.user.api.feign.MallUserFeignService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DT辰白 Created by 2021/12/22 13:52
 */
@CommonsLog
@Service
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements MallOrderService {

    @Autowired
    private MallSkuFeignService mallSkuFeignService;
    @Autowired
    private MallUserFeignService mallUserFeignService;

    //@Transactional(rollbackFor = Exception.class)
    //阿里巴巴的Java开发者手册里面有明确规定，在 @Transactional的方法里面捕获了异常，必须要手动回滚
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public ResultUtil<?> updateOrderStatus(Integer id, Integer userId) {
        MallOrder mallOrder = this.baseMapper.selectById(id);
        log.info("1、修改用户余额");
        mallUserFeignService.reduceMoney(mallOrder.getUserId());
        log.info("2、扣减库存->>>");
        ResultUtil<?> reduceStock = mallSkuFeignService.reduceStock(mallOrder.getGoodId());
        if(reduceStock.isSuccess()) {
            // 服务调用成功
            log.info("3、修改订单状态->>>");
            boolean result = this.update(new LambdaUpdateWrapper<MallOrder>().eq(MallOrder::getId, id).eq(MallOrder::getUserId,userId).set(MallOrder::getStatus, 1));
            if(result) {
                return ResultUtil.success("下单成功");
            }
        }else {
            return reduceStock;
        }
        return ResultUtil.error("下单失败");
    }

    @Override
    @Transactional
    public ResultUtil<?> updateTest(Integer id) {
        int n = 10/0;
        return ResultUtil.success(id);
    }
}
