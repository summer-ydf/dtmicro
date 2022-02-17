package com.cms.manage.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.service.SysMenuService;
import com.cms.manage.vo.SysMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ydf Created by 2022/2/17 15:53
 */
@Api(tags = "菜单管理API")
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "根据操作员ID获取菜单以及按钮权限信息")
    @GetMapping("/getOperatorMenu/{userId}")
    public ResultUtil<List<SysMenuVo>> getOperatorMenu(@PathVariable String userId) {
        return sysMenuService.listOperatorMenu(userId);
    }
}
