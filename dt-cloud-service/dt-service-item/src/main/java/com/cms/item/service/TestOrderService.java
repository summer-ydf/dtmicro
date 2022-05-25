package com.cms.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.item.entity.TestOrderEntity;

/**
 * @author DT辰白 Created by 2021/12/22 12:42
 */
public interface TestOrderService extends IService<TestOrderEntity> {

    ResultUtil<?> insert(Integer a);
}
