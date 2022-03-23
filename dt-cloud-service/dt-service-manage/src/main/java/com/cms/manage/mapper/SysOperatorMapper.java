package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysRoleDataScope;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.entity.SysOperatorRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ydf Created by 2022/1/7 17:23
 */
public interface SysOperatorMapper extends BaseMapper<SysOperatorEntity> {

    IPage<SysOperatorEntity> pageSearch(Page<SysOperatorEntity> page, SysSearchPage request);

    void saveOperatorRole(SysOperatorRoleEntity request);

    void deleteOperatorRoleByUserId(String userId);

    void updateOperatorRoleByUserId(Long userId, Long roleId);

    void deleteBath(@Param("array") long[] ids);

    void deleteBathOperatorRole(@Param("array") long[] ids);

    void removeOperatorRoleByUserId(Long id);

    List<Long> selectOperatorAndRoleById(Long id);

    List<SysRoleDataScope> selectRoleDataScopeByUserId(Long id);
}
