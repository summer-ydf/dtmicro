package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.result.ResultUtil;
import com.mall.order.entity.MallOrder;
import com.mall.order.mapper.MallOrderMapper;
import com.mall.order.service.MallOrderService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@CommonsLog
@Service
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements MallOrderService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> updateOrderStatus(Integer id, Integer userId) {
        log.info("修改订单状态->>>");
        boolean result = this.update(new LambdaUpdateWrapper<MallOrder>().eq(MallOrder::getId, id).eq(MallOrder::getUserId,userId).set(MallOrder::getStatus, 0));
        if(result) {
            int a = 10/0;
            return ResultUtil.success("下单成功");
        }else {
            return ResultUtil.success("下单失败");
        }
    }
}
