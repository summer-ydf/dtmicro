package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.log.annotation.Log;
import com.cms.common.log.enums.BusinessType;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOauthClientEntity;
import com.cms.manage.service.SysOauthClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DT辰白
 * @date 2022/4/28 19:56
 */
@Api(tags = "客户端管理API")
@RestController
@RequestMapping(value = "/client")
public class SysOauthClientController {

    @Autowired
    private SysOauthClientService sysOauthClientService;

    @ApiOperation(value = "分页查询客户端信息")
    @GetMapping("/page")
    public ResultUtil<IPage<SysOauthClientEntity>> page(SysSearchPage request) {
        return sysOauthClientService.pageSearch(request);
    }

    @Log(title = "编辑客户端记录", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "添加客户端")
    @PostMapping("/save")
    public ResultUtil<SysOauthClientEntity> save(@RequestBody SysOauthClientEntity request) {
        return sysOauthClientService.saveOauthClient(request);
    }

    @Log(title = "删除客户端记录", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除客户端")
    @DeleteMapping("/delete/{clientId}")
    public ResultUtil<SysOauthClientEntity> delete(@PathVariable String clientId) {
        return sysOauthClientService.deleteOauthClientByClientId(clientId);
    }
}
