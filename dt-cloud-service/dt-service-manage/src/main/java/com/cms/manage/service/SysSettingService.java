package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorSettingEntity;

/**
 * @author DT辰白
 * @date 2022/3/26 19:49
 */
public interface SysSettingService extends IService<SysOperatorSettingEntity> {

    ResultUtil<?> saveSetting(SysOperatorSettingEntity settingEntity);
}
