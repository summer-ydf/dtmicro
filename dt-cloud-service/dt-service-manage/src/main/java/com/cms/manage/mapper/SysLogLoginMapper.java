package com.cms.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.manage.entity.SysLogLoginEntity;

/**
 * 系统登录日志数据接口
 * @author DT
 * @date 2021/11/20 21:29
 */
public interface SysLogLoginMapper extends BaseMapper<SysLogLoginEntity> {

    IPage<SysLogLoginEntity> pageSearch(Page<SysLogLoginEntity> page, SysSearchPage request);
}
