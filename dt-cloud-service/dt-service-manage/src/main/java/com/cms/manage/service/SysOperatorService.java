package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.enums.AuthenticationIdentityEnum;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;

/**
 * @author DT辰白 Created by 2022/1/7 17:22
 */
public interface SysOperatorService extends IService<SysOperatorEntity> {

    ResultUtil<SecurityClaimsUserEntity> oauthAuthenticationByAccount(String account, AuthenticationIdentityEnum authenticationIdentityEnum);

    ResultUtil<IPage<SysOperatorEntity>> pageSearch(SysSearchPage request);

    ResultUtil<SysOperatorEntity> saveOperator(SysOperatorEntity request);

    ResultUtil<SysOperatorEntity> deleteOperatorById(String id);

    ResultUtil<?> deleteBath(long[] ids);

    ResultUtil<?> updateEnabled(Long id, Boolean enabled);
}
