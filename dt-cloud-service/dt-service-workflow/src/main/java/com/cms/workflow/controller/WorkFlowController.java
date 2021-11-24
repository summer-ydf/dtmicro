package com.cms.workflow.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/11/23 16:21
 */
@RestController
@RequestMapping(value = "/flow")
public class WorkFlowController {

    @GetMapping(value = "/getPort/{userId}")
//    @SentinelResource(value = "/getPort/{userId}",blockHandler = "testBlockHandler")
    public String getFlowPort(@PathVariable String userId) {
        return "端口返回：9999"+"--用户ID--"+userId;
    }

    @PostMapping(value = "/savePort")
    public String savePort() {
        return "添加成功";
    }

//    public String testBlockHandler(BlockException blockException) {
//        return "自定义流控："+ blockException;
//    }
}
