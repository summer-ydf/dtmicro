package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.entity.SysPermissionEntity;
import com.cms.manage.entity.SysRoleEntity;

import java.util.List;

/**
 * 系统角色数据接口
 * @author DT
 * @date 2021/6/2 22:24
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    List<SysMenuEntity> getMenuListByRoleId(Long id);

    List<Long> listRolePermissionByRoleId(Long id);

    void deleteRolePermissionByIds(List<Long> ids);
}
