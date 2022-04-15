package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.service.MqMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2022/4/15 10:55
 */
@Api(tags = "MQ信息日志API")
@RestController
@RequestMapping("/message")
public class MqMessageController {

    private final MqMessageService mqMessageService;

    public MqMessageController(MqMessageService mqMessageService) {
        this.mqMessageService = mqMessageService;
    }

    @ApiOperation(value = "查询MQ消息日志分页")
    @GetMapping("/page")
    public ResultUtil<IPage<MqMessageEntity>> loginPage(SysSearchPage request) {
        return mqMessageService.pageSearch(request);
    }

    @ApiOperation(value = "单个删除MQ消息日志")
    @DeleteMapping("/delete/{id}")
    public ResultUtil<?> deleteMessageById(@PathVariable String id) {
        return mqMessageService.deleteMessageById(id);
    }

    @ApiOperation(value = "批量删除MQ消息日志")
    @DeleteMapping("/bath_delete")
    public ResultUtil<?> deleteBathMessage(@RequestBody String[] ids) {
        return mqMessageService.deleteBathMessage(ids);
    }

}
