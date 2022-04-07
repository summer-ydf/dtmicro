package com.cms.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.document.entity.FileInformationEntity;

/**
 * @author ydf Created by 2022/4/7 10:29
 */
public interface FileInformationMapper extends BaseMapper<FileInformationEntity> {

    IPage<FileInformationEntity> pageSearch(Page<FileInformationEntity> page, SysSearchPage request);
}
