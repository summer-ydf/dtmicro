package com.cms.manage.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
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
    public ResultUtil<SysOperatorLogVoEntity> saveOperatorLog(SysOperatorLogEntity sysOperatorLogEntity) {
        int insert = this.baseMapper.insert(sysOperatorLogEntity);
        return insert > 0 ? ResultUtil.success() : ResultUtil.error();
    }

}
