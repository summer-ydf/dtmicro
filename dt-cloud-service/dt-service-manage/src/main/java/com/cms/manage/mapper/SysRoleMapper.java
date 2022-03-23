package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.entity.SysRoleEntity;
import com.cms.manage.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 系统角色数据接口
 * @author DT
 * @date 2021/6/2 22:24
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    List<SysMenuEntity> getMenuListByRoleId(Long id);

    List<Long> listRoleMenuByRoleId(Long id);

    void deleteRoleMenuByIds(List<Long> ids);

    IPage<SysRoleEntity> pageSearch(Page<SysRoleEntity> page, SysSearchPage request);

    Long selectOperotarRoleByRoleId(long id);

    void deleteBath(List<Long> ids);

    List<SysRoleMenuEntity> selectRoleMenuList(Long roleId);

    void deleteBathRoleMenu(List<Long> ids);

    void insertRoleMenu(SysRoleMenuEntity roleMenuEntity);
}
