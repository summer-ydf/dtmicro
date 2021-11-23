package com.cms.manage.controller;

import com.cms.manage.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/11/23 15:40
 */
@RestController
@RequestMapping(value = "/api")
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    @GetMapping(value = "/test")
    public String test() {
        return workFlowService.getTest();
    }
}
