package com.cms.manage.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysLogOperatorEntity;
import com.cms.manage.entity.SysRoleEntity;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
public interface SysLogOperatorService extends IService<SysLogOperatorEntity> {

    ResultUtil<SysOperatorLogVoEntity> saveOperatorLog(SysLogOperatorEntity sysOperatorLogEntity);

    ResultUtil<IPage<SysLogOperatorEntity>> pageSearch(SysSearchPage request);
}
