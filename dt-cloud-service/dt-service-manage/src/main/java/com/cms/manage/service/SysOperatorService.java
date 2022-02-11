package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.entity.SecurityClaimsUser;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.vo.SysOperatorPage;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
public interface SysOperatorService extends IService<SysOperatorEntity> {

    ResultUtil<SecurityClaimsUser> loadUserByUsername(String username, String scope);

    ResultUtil<IPage<SysOperatorEntity>> pageSearch(SysOperatorPage request);

    ResultUtil<SysOperatorEntity> saveOperator(SysOperatorEntity request);

    ResultUtil<SysOperatorEntity> deleteOperatorById(Long id);

    ResultUtil<SysOperatorEntity> updateOperatorById(SysOperatorEntity request);
}
