package com.cms.job.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.job.entity.JobInformationEntity;
import com.cms.job.mapper.JobMapper;
import com.cms.job.service.JobService;
import org.springframework.stereotype.Service;

/**
 * @author DT
 * @date 2022/3/28 19:27
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, JobInformationEntity> implements JobService {

    @Override
    public ResultUtil<IPage<JobInformationEntity>> pageSearch(SysSearchPage request) {
        Page<JobInformationEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<JobInformationEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }
}
