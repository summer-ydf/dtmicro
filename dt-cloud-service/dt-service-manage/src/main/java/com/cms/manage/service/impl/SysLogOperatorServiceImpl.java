package com.cms.manage.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysLogOperatorEntity;
import com.cms.manage.mapper.SysLogOperatorMapper;
import com.cms.manage.service.SysLogOperatorService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统操作日志服务实现类
 * @author DT辰白
 * @date 2021/11/20 17:37
 */
@CommonsLog
@Service
public class SysLogOperatorServiceImpl extends ServiceImpl<SysLogOperatorMapper, SysLogOperatorEntity> implements SysLogOperatorService {

    @Async("sysTaskExecutor")
    @Override
    public ResultUtil<SysOperatorLogVoEntity> saveOperatorLog(SysLogOperatorEntity sysOperatorLogEntity) {
        int insert = this.baseMapper.insert(sysOperatorLogEntity);
        return insert > 0 ? ResultUtil.success() : ResultUtil.error();
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<IPage<SysLogOperatorEntity>> pageSearch(SysSearchPage request) {
        Page<SysLogOperatorEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysLogOperatorEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> deleteBathOperator(long[] ids) {
        this.baseMapper.deleteBathOperator(ids);
        return ResultUtil.success();
    }

}
