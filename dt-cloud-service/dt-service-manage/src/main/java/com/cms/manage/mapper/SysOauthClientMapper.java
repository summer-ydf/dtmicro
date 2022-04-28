package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.manage.entity.SysOauthClientEntity;

/**
 * @author ydf Created by 2022/4/28 15:06
 */
public interface SysOauthClientMapper extends BaseMapper<SysOauthClientEntity> {

    IPage<SysOauthClientEntity> pageSearch(Page<SysOauthClientEntity> page, SysSearchPage request);
}
