package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.manage.entity.SysDicEntity;

/**
 * @author ydf Created by 2022/3/30 11:20
 */
public interface SysDicMapper extends BaseMapper<SysDicEntity> {

    IPage<SysDicEntity> pageSearch(Page<SysDicEntity> page, SysSearchPage request);

    void deleteBathDic(long[] ids);
}
