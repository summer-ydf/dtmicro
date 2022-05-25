package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.log.annotation.Log;
import com.cms.common.log.enums.BusinessType;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysDicEntity;
import com.cms.manage.service.SysDicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DT辰白 Created by 2022/3/30 11:35
 */
@Api(tags = "字典管理API")
@RestController
@RequestMapping(value = "/dic")
public class SysDicController {

    private final SysDicService sysDicService;

    public SysDicController(SysDicService sysDicService) {
        this.sysDicService = sysDicService;
    }

    @ApiOperation(value = "分页查询字典列表")
    @GetMapping("/page")
    public ResultUtil<IPage<SysDicEntity>> page(SysSearchPage request) {
        return sysDicService.pageSearch(request);
    }

    @Log(title = "编辑字典日志记录", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "添加字典")
    @PostMapping("/save")
    public ResultUtil<SysDicEntity> save(@RequestBody SysDicEntity request) {
        return sysDicService.saveDic(request);
    }

    @Log(title = "删除字典日志记录", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除字典")
    @DeleteMapping("/delete/{id}")
    public ResultUtil<SysDicEntity> delete(@PathVariable Long id) {
        return sysDicService.deleteDicById(id);
    }

    @Log(title = "批量删除字典日志记录", businessType = BusinessType.DELETE)
    @ApiOperation(value = "批量删除字典")
    @DeleteMapping("/delete_bath")
    public ResultUtil<?> deleteBath(@RequestBody long[] ids) {
        return sysDicService.deleteBath(ids);
    }

    @ApiOperation(value = "禁用/启用字典状态")
    @DeleteMapping("/update_enabled/{id}/{enabled}")
    public ResultUtil<?> updateEnabled(@PathVariable Long id, @PathVariable Boolean enabled) {
        return sysDicService.updateEnabled(id,enabled);
    }
}
