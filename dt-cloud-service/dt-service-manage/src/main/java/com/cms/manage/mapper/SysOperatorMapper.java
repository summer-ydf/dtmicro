package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.entity.SysOperatorRoleEntity;
import com.cms.manage.vo.SysOperatorPage;

/**
 * @author ydf Created by 2022/1/7 17:23
 */
public interface SysOperatorMapper extends BaseMapper<SysOperatorEntity> {

    IPage<SysOperatorEntity> pageSearch(Page<SysOperatorEntity> page, SysOperatorPage request);

    void saveOperatorRole(SysOperatorRoleEntity request);

    void deleteOperatorRoleByUserId(Long userId);

    void updateOperatorRoleByUserId(Long userId, Long roleId);
}
