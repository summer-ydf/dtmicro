package com.cms.manage.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ydf Created by 2022/2/17 15:53
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
    @GetMapping("/getOperatorMenu/{userId}")
    public ResultUtil<Map<String,Object>> getOperatorMenu(@PathVariable String userId) {
        return sysMenuService.listOperatorMenu(userId);
    }
}
