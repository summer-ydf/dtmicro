package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.search.SysSearchPage;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
public interface SysOperatorService extends IService<SysOperatorEntity> {

    ResultUtil<SecurityClaimsUserEntity> loadUserByUsername(String username, String scope);

    ResultUtil<IPage<SysOperatorEntity>> pageSearch(SysSearchPage request);

    ResultUtil<SysOperatorEntity> saveOperator(SysOperatorEntity request);

    ResultUtil<SysOperatorEntity> deleteOperatorById(String id);

    ResultUtil<?> deleteBath(long[] ids);
}
