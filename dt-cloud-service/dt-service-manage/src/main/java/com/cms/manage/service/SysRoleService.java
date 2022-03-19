package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.search.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysRoleEntity;

import java.util.List;

/**
 * 系统角色服务接口
 * @author DT
 * @date 2021/6/2 22:19
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    ResultUtil<IPage<SysRoleEntity>> pageSearch(SysSearchPage request);

    ResultUtil<List<SysRoleEntity>> queryList(SysRoleEntity request);

    ResultUtil<SysRoleEntity> saveRole(SysRoleEntity request);

    ResultUtil<SysRoleEntity> getRoleById(Long id);

    ResultUtil<SysRoleEntity> deleteRoleById(Long id);

    ResultUtil<List<SysRoleEntity>> findAll();

    ResultUtil<?> deleteBath(long[] ids);
}
