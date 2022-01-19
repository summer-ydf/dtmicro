package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.result.ResultUtil;
import com.cms.manage.entity.SysPermissionEntity;

import java.util.List;

/**
 * 系统权限服务接口
 * @author DT
 * @date 2021/6/2 22:19
 */
public interface SysPermissionService extends IService<SysPermissionEntity> {

    List<SysPermissionEntity> getPermissionListByUserId(Long userId);

    ResultUtil<List<SysPermissionEntity>> queryList(SysPermissionEntity request);

    ResultUtil<SysPermissionEntity> saveMenu(SysPermissionEntity request);

    ResultUtil<SysPermissionEntity> getMenuById(Long id);

    ResultUtil<SysPermissionEntity> updatePermissionById(SysPermissionEntity request);

    ResultUtil<SysPermissionEntity> deletePermissionById(Long id);

    List<SysPermissionEntity> getPermissionListByRoleId(Long roleId);

    ResultUtil<List<SysPermissionEntity>> listTree();
}
