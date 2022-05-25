package com.cms.manage.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DT辰白 Created by 2022/2/17 15:53
 */
@Api(tags = "菜单管理API")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @ApiOperation(value = "获取菜单列表")
    @GetMapping("/list")
    public ResultUtil<List<SysMenuEntity>> list() {
        return sysMenuService.listMenu();
    }

    @ApiOperation(value = "根据操作员ID获取菜单以及按钮权限信息")
    @GetMapping("/get_operator_menu/{userId}")
    public ResultUtil<Map<String,Object>> getOperatorMenu(@PathVariable String userId) {
        return sysMenuService.listOperatorMenu(userId);
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("/save")
    public ResultUtil<SysMenuEntity> generateId(@RequestBody SysMenuEntity sysMenuEntity) {
        return sysMenuService.saveMenu(sysMenuEntity);
    }

    @ApiOperation(value = "批量删除菜单")
    @DeleteMapping("/deleteBath")
    public ResultUtil<?> deleteBath(@RequestBody Map<String,Object> map) {
        // 接收List
        List<String> ids = (List<String>) map.get("ids");
        Map<String, String> stringMap = new HashMap<>(2);
        ids.forEach(id -> stringMap.put("ids", id));
        return sysMenuService.deleteBath(ids);
    }
}
