package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cms.manage.entity.SysPermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统权限数据接口
 * @author DT
 * @date 2021/6/2 22:24
 */
public interface SysPermissionMapper extends BaseMapper<SysPermissionEntity> {

    List<SysPermissionEntity> getPermissionListByUserId(@Param("userId") Long userId);

    List<SysPermissionEntity> queryList(@Param("request") SysPermissionEntity request);

    List<SysPermissionEntity> getPermissionListByRoleId(@Param("roleId") Long roleId);

    int countRolePermission(@Param("id") Long id);
}
