package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.result.ResultUtil;
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
    public ResultUtil<SysOperatorEntity> save(@RequestBody SysOperatorEntity request){
        return sysOperatorService.saveOperator(request);
    }

//    @GetMapping("getById/{id}")
//    @ApiOperation(value = "根据id查询用户")
//    public ResultUtil<SysUserResponse> getById(@PathVariable Long id){
//        return sysUserService.getUserById(id);
//    }
//
//    @PutMapping("update")
//    @ApiOperation(value = "修改系统用户")
//    public ResultUtil<SysUserSaveRequest> update(@RequestBody @Valid SysUserSaveRequest request){
//        return sysUserService.updateUser(request);
//    }
//
//    @DeleteMapping("delete/{id}")
//    @ApiOperation(value = "删除系统部门")
//    public ResultUtil<SysUserEntity> delete(@PathVariable Long id){
//        return sysUserService.deleteUser(id);
//    }
}
