package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.log.annotation.Log;
import com.cms.common.log.enums.BusinessType;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.vo.SysOperatorPage;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ydf Created by 2022/1/12 15:59
 */
@Api(tags = "操作员管理API")
@RestController
@RequestMapping(value = "/operator")
public class SysOperatorController {

    private final SysOperatorService sysOperatorService;

    public SysOperatorController(SysOperatorService sysOperatorService) {
        this.sysOperatorService = sysOperatorService;
    }

    @ApiOperation(value = "分页查询用户列表")
    @GetMapping("/page")
    public ResultUtil<IPage<SysOperatorEntity>> page(SysOperatorPage request) {
        return sysOperatorService.pageSearch(request);
    }

    @ApiOperation(value = "添加操作员")
    @PostMapping("/save")
    public ResultUtil<SysOperatorEntity> save(@RequestBody SysOperatorEntity request) {
        return sysOperatorService.saveOperator(request);
    }

    @ApiOperation(value = "根据id获取操作员信息")
    @GetMapping("/getById/{id}")
    public ResultUtil<SysOperatorEntity> getById(@PathVariable Long id) {
        SysOperatorEntity operator = sysOperatorService.getById(id);
        return ResultUtil.success(operator);
    }

    @ApiOperation(value = "修改操作员")
    @PutMapping("/update")
    public ResultUtil<SysOperatorEntity> update(@RequestBody SysOperatorEntity request) {
        return sysOperatorService.updateOperatorById(request);
    }

    @Log(title = "删除操作员日志记录", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/delete/{id}")
    public ResultUtil<SysOperatorEntity> delete(@PathVariable String id) {
        return sysOperatorService.deleteOperatorById(id);
    }

    @ApiOperation(value = "批量删除操作员")
    @DeleteMapping("/deleteBath")
    public ResultUtil<?> deleteBath(@RequestBody Map<String,Object> map) {
        // 接收List
        List<String> ids = (List<String>) map.get("ids");
        Map<String, String> stringMap = new HashMap<>(2);
        ids.forEach(id -> stringMap.put("ids", id));
        return sysOperatorService.deleteBath(ids);
    }
}
