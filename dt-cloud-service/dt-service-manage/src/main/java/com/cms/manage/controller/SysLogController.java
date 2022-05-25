package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysLogLoginEntity;
import com.cms.manage.entity.SysLogOperatorEntity;
import com.cms.manage.service.SysLogLoginService;
import com.cms.manage.service.SysLogOperatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DT辰白
 * @date 2022/3/24 21:53
 */
@Api(tags = "日志管理API")
@RestController
@RequestMapping(value = "/log")
public class SysLogController {

    private final SysLogLoginService sysLogLoginService;
    private final SysLogOperatorService sysLogOperatorService;

    public SysLogController(SysLogLoginService sysLogLoginService, SysLogOperatorService sysLogOperatorService) {
        this.sysLogLoginService = sysLogLoginService;
        this.sysLogOperatorService = sysLogOperatorService;
    }

    @ApiOperation(value = "分页查询登录日志列表")
    @GetMapping("/login_page")
    public ResultUtil<IPage<SysLogLoginEntity>> loginPage(SysSearchPage request) {
        return sysLogLoginService.pageSearch(request);
    }

    @ApiOperation(value = "分页查询操作日志列表")
    @GetMapping("/operator_page")
    public ResultUtil<IPage<SysLogOperatorEntity>> operatorPage(SysSearchPage request) {
        return sysLogOperatorService.pageSearch(request);
    }

    @ApiOperation(value = "批量删除登录日志")
    @DeleteMapping("/bath_delete_login")
    public ResultUtil<?> deleteBathLogin(@RequestBody long[] ids) {
        return sysLogLoginService.deleteBathLogin(ids);
    }

    @ApiOperation(value = "批量删除操作日志")
    @DeleteMapping("/bath_delete_operator")
    public ResultUtil<?> deleteBathOperator(@RequestBody long[] ids) {
        return sysLogOperatorService.deleteBathOperator(ids);
    }

    @ApiOperation(value = "单个删除登录日志")
    @DeleteMapping("/delete_login/{id}")
    public ResultUtil<?> deleteLogin(@PathVariable Long id) {
        sysLogLoginService.removeById(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "单个删除操作日志")
    @DeleteMapping("/delete_operator/{id}")
    public ResultUtil<?> deleteOperator(@PathVariable Long id) {
        sysLogOperatorService.removeById(id);
        return ResultUtil.success();
    }

}
