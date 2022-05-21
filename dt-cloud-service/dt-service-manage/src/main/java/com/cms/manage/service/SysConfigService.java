package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.Params;
import com.cms.common.core.domain.SysConfig;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysConfigEntity;

import java.util.List;

/**
 * @author DT
 * @date 2022/4/17 13:46
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    ResultUtil<?> listConfigs();

    ResultUtil<?> saveConfigs(List<SysConfig> sysConfigs);

    List<SysConfig> selectConfigList();
}
