package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.manage.entity.SysLoginLogEntity;
import com.cms.manage.mapper.SysLoginLogMapper;
import com.cms.manage.service.SysLoginLogService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 系统登录日志数据实现类
 * @author DT
 * @date 2021/11/20 21:28
 */
@CommonsLog
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLogEntity> implements SysLoginLogService {

    @Async("sysTaskExecutor")
    @Override
    public void saveLoginLog(SysLoginLogEntity loginLogEntity) {
        this.baseMapper.insert(loginLogEntity);
    }
}
