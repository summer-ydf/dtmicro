package com.cms.manage.controller;

import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.domain.Params;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysConfigEntity;
import com.cms.manage.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DT
 * @date 2022/4/17 13:50
 */
@Api(tags = "系统配置API")
@RestController
@RequestMapping("/config")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    public SysConfigController(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    @ApiOperation(value = "获取系统配置信息")
    @GetMapping("/list")
    public ResultUtil<?> listConfigs() {
        return sysConfigService.listConfigs();
    }

    @ApiOperation(value = "添加系统配置信息")
    @PostMapping("/save")
    public ResultUtil<List<SysConfigEntity>> save(@RequestBody List<SysConfigEntity> sysConfigs) {
        System.out.println("SysConfigEntity==========="+sysConfigs);
        //SettingBuilders.settingParamsToList(new Params().p("1","1"));
        return ResultUtil.success(sysConfigs);
    }
}
