package com.cms.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.manage.entity.SysLogOperatorEntity;

/**
 * 系统操作日志数据接口
 * @author DT
 * @date 2021/11/20 17:37
 */
public interface SysLogOperatorMapper extends BaseMapper<SysLogOperatorEntity> {

    IPage<SysLogOperatorEntity> pageSearch(Page<SysLogOperatorEntity> page, SysSearchPage request);
}
