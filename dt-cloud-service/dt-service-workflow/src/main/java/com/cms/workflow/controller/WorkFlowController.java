package com.cms.workflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/11/23 16:21
 */
@RestController
@RequestMapping(value = "/flow")
public class WorkFlowController {

    @GetMapping(value = "/getPort")
    public String getFlowPort() {
        return "端口返回：9999";
    }
}
