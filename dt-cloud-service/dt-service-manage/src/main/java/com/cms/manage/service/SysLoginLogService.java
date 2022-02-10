package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.manage.entity.SysLoginLogEntity;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
public interface SysLoginLogService extends IService<SysLoginLogEntity> {

    void saveLoginLog(SysLoginLogEntity loginLogEntity);
}
