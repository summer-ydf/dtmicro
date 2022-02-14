package com.cms.manage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorLogEntity;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
public interface SysOperatorLogService extends IService<SysOperatorLogEntity> {

    ResultUtil<SysOperatorLogVoEntity> saveOperatorLog(SysOperatorLogEntity sysOperatorLogEntity);
}
