package com.cms.manage.controller;


import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysPermissionEntity;
import com.cms.manage.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author DT
 * @date 2021/6/5 0:33
 */
@Api(tags = "菜单权限管理API")
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @ApiOperation(value = "查询菜单树列表")
    @GetMapping("/list")
    public ResultUtil<List<SysPermissionEntity>> list(SysPermissionEntity request) {
        return sysPermissionService.queryList(request);
    }

    @ApiOperation(value = "获取所有权限数据")
    @GetMapping("/listTree")
    public ResultUtil<List<SysPermissionEntity>> listTree() {
        return sysPermissionService.listTree();
    }

    @ApiOperation(value = "添加系统菜单")
    @PostMapping("/save")
    public ResultUtil<SysPermissionEntity> save(@RequestBody SysPermissionEntity request) {
        return sysPermissionService.saveMenu(request);
    }

    @ApiOperation(value = "根据id获取菜单信息")
    @GetMapping("/getById/{id}")
    public ResultUtil<SysPermissionEntity> getById(@PathVariable Long id) {
        return sysPermissionService.getMenuById(id);
    }

    @ApiOperation(value = "修改系统菜单")
    @PutMapping("/update")
    public ResultUtil<SysPermissionEntity> update(@RequestBody SysPermissionEntity request) {
        return sysPermissionService.updatePermissionById(request);
    }

    @ApiOperation(value = "删除系统菜单")
    @DeleteMapping("delete/{id}")
    public ResultUtil<SysPermissionEntity> delete(@PathVariable Long id) {
        return sysPermissionService.deletePermissionById(id);
    }
}
