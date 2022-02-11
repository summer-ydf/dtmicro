package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.TestProductEntity;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
public interface TestProductService extends IService<TestProductEntity> {

    void deductProduct(Integer a,String xid);

    ResultUtil<?> insert();
}
