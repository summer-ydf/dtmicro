package com.mall.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.mall.sku.entity.MallSku;

/**
 * @author DT辰白 Created by 2021/12/22 13:52
 */
public interface MallSkuService extends IService<MallSku> {

    ResultUtil<?> reduceStock(Integer id);
}
