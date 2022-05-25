package com.cms.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.job.entity.JobInformationEntity;

/**
 * @author DT辰白
 * @date 2022/3/28 19:27
 */
public interface JobMapper extends BaseMapper<JobInformationEntity> {

    IPage<JobInformationEntity> pageSearch(Page<JobInformationEntity> page, SysSearchPage request);
}
