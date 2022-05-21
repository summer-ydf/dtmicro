package com.cms.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.entity.WxMessageEntity;
import com.cms.manage.service.MessageService;
import com.cms.manage.vo.WxMessageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ydf Created by 2022/4/15 10:55
 */
@Api(tags = "信息日志API")
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiOperation(value = "查询MQ消息日志分页")
    @GetMapping("/mq_page")
    public ResultUtil<IPage<MqMessageEntity>> mqPage(SysSearchPage request) {
        return messageService.pageMqSearch(request);
    }

    @ApiOperation(value = "单个删除MQ消息日志")
    @DeleteMapping("/delete_mq/{id}")
    public ResultUtil<?> deleteMqMessageById(@PathVariable String id) {
        return messageService.deleteMqMessageById(id);
    }

    @ApiOperation(value = "批量删除MQ消息日志")
    @DeleteMapping("/bath_delete_mq")
    public ResultUtil<?> deleteBathMqMessage(@RequestBody String[] ids) {
        return messageService.deleteBathMqMessage(ids);
    }

    @ApiOperation(value = "发送微信公众号模板信息")
    @PostMapping("/wx_send_message")
    public ResultUtil<?> save(@RequestBody WxMessageRequest wxMessageRequest) {
        return messageService.wxSendMessage(wxMessageRequest);
    }

    @ApiOperation(value = "查询微信公众号消息日志分页")
    @GetMapping("/wx_page")
    public ResultUtil<IPage<WxMessageEntity>> wxPage(SysSearchPage request) {
        return messageService.pageWxSearch(request);
    }

    @ApiOperation(value = "单个删除微信公众号消息日志")
    @DeleteMapping("/delete_wx/{id}")
    public ResultUtil<?> deleteWxMessageById(@PathVariable String id) {
        return messageService.deleteWxMessageById(id);
    }

    @ApiOperation(value = "批量删除微信公众号消息日志")
    @DeleteMapping("/bath_delete_wx")
    public ResultUtil<?> deleteBathWxMessage(@RequestBody String[] ids) {
        return messageService.deleteBathWxMessage(ids);
    }
}
