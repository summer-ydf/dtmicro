package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.vo.SysOperatorPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2022/1/12 15:59
 */
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
}
