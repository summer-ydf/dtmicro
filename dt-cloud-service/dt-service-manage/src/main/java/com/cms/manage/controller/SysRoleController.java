package com.cms.manage.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysRoleEntity;
import com.cms.manage.service.SysRoleService;
import com.cms.manage.vo.SysRoleMenuData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author DT
 * @date 2021/6/5 0:32
 */
@Api(tags = "角色管理API")
@RestController
@RequestMapping(value = "/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @ApiOperation(value = "分页查询角色列表")
    @GetMapping("/page")
    public ResultUtil<IPage<SysRoleEntity>> page(SysSearchPage request) {
        return sysRoleService.pageSearch(request);
    }

    @ApiOperation(value = "查询角色权限列表")
    @GetMapping("/list")
    public ResultUtil<List<SysRoleEntity>> list(SysRoleEntity request){
        return sysRoleService.queryList(request);
    }

    //@Log(title = "新增平台系统角色", businessType = LogTypeCode.INSERT)
    @ApiOperation(value = "添加系统角色")
    @PostMapping("/save")
    public ResultUtil<SysRoleEntity> save(@RequestBody SysRoleEntity request) {
        return sysRoleService.saveRole(request);
    }

    @ApiOperation(value = "根据id查询角色")
    @GetMapping("/getById/{id}")
    public ResultUtil<SysRoleEntity> getById(@PathVariable Long id){
        return sysRoleService.getRoleById(id);
    }

    //@Log(title = "删除平台系统角色", businessType = LogTypeCode.DELETE)
    @ApiOperation(value = "删除系统角色")
    @DeleteMapping("/delete/{id}")
    public ResultUtil<SysRoleEntity> delete(@PathVariable Long id){
        return sysRoleService.deleteRoleById(id);
    }

    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("/delete_bath")
    public ResultUtil<?> deleteBath(@RequestBody long[] ids) {
        return sysRoleService.deleteBath(ids);
    }

    @ApiOperation(value = "查询所有角色")
    @GetMapping("/findAll")
    public ResultUtil<List<SysRoleEntity>> findAll(){
        return sysRoleService.findAll();
    }

    @ApiOperation(value = "根据角色ID获取角色菜单权限信息")
    @GetMapping("/getTreeRoleMenuById/{id}")
    public ResultUtil<SysRoleEntity> getTreeRoleMenuById(@PathVariable Long id) {
        return sysRoleService.getTreeRoleMenuById(id);
    }

    @ApiOperation(value = "添加角色菜单权限")
    @PostMapping("/saveRoleMenu")
    public ResultUtil<?> saveRoleMenu(@RequestBody SysRoleMenuData sysRoleMenuData) {
        return sysRoleService.saveRoleMenu(sysRoleMenuData);
    }
}
