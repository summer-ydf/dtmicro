package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.core.service.FileProvider;
import com.cms.common.log.annotation.Log;
import com.cms.common.log.enums.BusinessType;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.service.SysOperatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ydf Created by 2022/1/12 15:59
 */
@Api(tags = "操作员管理API")
@RestController
@RequestMapping(value = "/operator")
public class SysOperatorController {

    private final FileProvider fileProvider;

    private final SysOperatorService sysOperatorService;

    public SysOperatorController(SysOperatorService sysOperatorService, FileProvider fileProvider) {
        this.sysOperatorService = sysOperatorService;
        this.fileProvider = fileProvider;
    }

    @ApiOperation(value = "分页查询用户列表")
    @GetMapping("/page")
    public ResultUtil<IPage<SysOperatorEntity>> page(SysSearchPage request, HttpServletRequest servletRequest) {
        return sysOperatorService.pageSearch(request);
    }

    @Log(title = "编辑操作员日志记录", businessType = BusinessType.UPDATE)
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

    @Log(title = "删除操作员日志记录", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/delete/{id}")
    public ResultUtil<SysOperatorEntity> delete(@PathVariable String id) {
        return sysOperatorService.deleteOperatorById(id);
    }

    @Log(title = "批量删除操作员日志记录", businessType = BusinessType.DELETE)
    @ApiOperation(value = "批量删除操作员")
    @DeleteMapping("/delete_bath")
    public ResultUtil<?> deleteBath(@RequestBody long[] ids) {
        return sysOperatorService.deleteBath(ids);
    }

    @ApiOperation(value = "禁用/启用操作员状态")
    @DeleteMapping("/update_enabled/{id}/{enabled}")
    public ResultUtil<?> updateEnabled(@PathVariable Long id, @PathVariable Boolean enabled) {
        return sysOperatorService.updateEnabled(id,enabled);
    }

    @ApiOperation(value = "获取头像地址")
    @GetMapping("/getAvatarUrl/{userId}")
    public String getAvatarUrl(@PathVariable Long userId) {
        return sysOperatorService.getAvatarUrl(userId);
    }

    @ApiOperation(value = "上传头像")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文件",name = "file",required = true,allowMultiple = true,dataType = "MultipartFile")
    })
    @PostMapping(value = "/uploadFile",headers = "content-type=multipart/form-data")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return fileProvider.putObject(file, null);
    }

}
