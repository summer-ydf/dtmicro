package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorSettingEntity;
import com.cms.manage.service.SysSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author DT
 * @date 2022/3/26 19:48
 */
@Api(tags = "操作员自定义设置管理API")
@RestController
@RequestMapping(value = "/setting")
public class SysSettingController {

    private final SysSettingService sysSettingService;

    public SysSettingController(SysSettingService sysSettingService) {
        this.sysSettingService = sysSettingService;
    }

    @ApiOperation(value = "添加我的常用应用")
    @PostMapping("/save")
    public ResultUtil<?> save(@RequestBody SysOperatorSettingEntity settingEntity) {
        return sysSettingService.saveSetting(settingEntity);
    }

    @ApiOperation(value = "获取我的常用应用")
    @GetMapping("/get_my_setting/{userId}")
    public ResultUtil<SysOperatorSettingEntity> getMySetting(@PathVariable Long userId) {
        SysOperatorSettingEntity settingEntity = this.sysSettingService.getOne(new QueryWrapper<SysOperatorSettingEntity>().eq("user_id", userId));
        return ResultUtil.success(settingEntity);
    }
}
