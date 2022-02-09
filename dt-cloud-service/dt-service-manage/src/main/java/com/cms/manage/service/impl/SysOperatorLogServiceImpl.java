package com.cms.manage.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.manage.entity.SysOperatorLogEntity;
import com.cms.manage.mapper.SysOperatorLogMapper;
import com.cms.manage.service.SysOperatorLogService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 系统操作日志服务实现类
 * @author DT
 * @date 2021/11/20 17:37
 */
@CommonsLog
@Service
public class SysOperatorLogServiceImpl extends ServiceImpl<SysOperatorLogMapper, SysOperatorLogEntity> implements SysOperatorLogService {

    @Async("sysTaskExecutor")
    @Override
    public void saveOperatorLog(SysOperatorLogEntity sysLog) {
        this.baseMapper.insert(sysLog);
        log.info("===============操作日志成功写入数据库===============");
    }

}
