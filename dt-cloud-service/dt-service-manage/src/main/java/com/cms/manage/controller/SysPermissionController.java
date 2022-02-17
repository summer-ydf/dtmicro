package com.cms.manage.controller;


import com.alibaba.fastjson.JSON;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysPermissionEntity;
import com.cms.manage.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author DT
 * @date 2021/6/5 0:33
 */
@Api(tags = "菜单权限管理API")
@RestController
@RequestMapping("/api/permission")
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

    @ApiOperation(value = "登录测试")
    @PostMapping("/login")
    public ResultUtil<?> login() {
        Map<String,Object> objectMap = new HashMap<>(2);
        objectMap.put("token","SCUI.Administrator.Auth");
        Map<String,Object> map = new HashMap<>(2);
        map.put("dashboard",0);
        map.put("userId","1");
        map.put("userName","Admin");
        map.put("role",new String[] {"SA", "admin", "Auditor"});
        objectMap.put("userInfo",map);
        return ResultUtil.success(objectMap);
    }

    @ApiOperation(value = "根据操作员ID获取菜单以及按钮权限信息")
    @GetMapping("/menu/{userId}")
    public ResultUtil<?> getSystemMenu(@PathVariable String userId) throws IOException {
        Map<String,Object> objectMap = new HashMap<>(2);
        // 获取菜单数据
        File menuJsonFile = ResourceUtils.getFile("classpath:menu.json");
        String menuJsonInfo = FileUtils.readFileToString(menuJsonFile);
        objectMap.put("menu",JSON.parseArray(menuJsonInfo));
        // 获取按钮权限数据
        File permissionsJsonFile = ResourceUtils.getFile("classpath:permissions.json");
        String permissionsJsonInfo = FileUtils.readFileToString(permissionsJsonFile);
        objectMap.put("permissions",JSON.parseArray(permissionsJsonInfo));
        return ResultUtil.success(objectMap);
    }
}
