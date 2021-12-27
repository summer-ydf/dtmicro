package com.mall.sku.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.result.ResultUtil;
import com.mall.sku.entity.MallSku;
import com.mall.sku.mapper.MallSkuMapper;
import com.mall.sku.service.MallSkuService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@Service
@CommonsLog
public class MallSkuServiceImpl extends ServiceImpl<MallSkuMapper, MallSku> implements MallSkuService {

    @Override
    public ResultUtil<?> reduceStock(Integer id) {
        log.info("开始修改库存->>>");
        MallSku mallSku = this.baseMapper.selectById(id);
        Integer stock = mallSku.getStock() - 1;
        mallSku.setStock(stock);
        this.baseMapper.updateById(mallSku);
        // 抛出异常，fallback里面控制手动回滚
        int a = 10 /0;
        log.info("修改库存成功->>>");
        return ResultUtil.success();
    }
}
