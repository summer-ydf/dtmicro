package com.cms.manage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.manage.entity.SysOperatorLogEntity;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
public interface SysOperatorLogService extends IService<SysOperatorLogEntity> {

    void saveOperatorLog(SysOperatorLogEntity sysLog);
}
