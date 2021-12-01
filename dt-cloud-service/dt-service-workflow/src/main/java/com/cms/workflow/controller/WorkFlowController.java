package com.cms.workflow.controller;

import com.cms.common.result.ResultUtil;
import com.cms.workflow.entity.FlowInstanceEntity;
import com.cms.workflow.service.FlowInstanceService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ydf Created by 2021/11/23 16:21
 */
@RestController
@RequestMapping(value = "/flow")
public class WorkFlowController {

    @Autowired
    private FlowInstanceService flowInstanceService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;

    @GetMapping(value = "/getPort/{userId}")
//    @SentinelResource(value = "/getPort/{userId}",blockHandler = "testBlockHandler")
    public ResultUtil<?> getFlowPort(@PathVariable String userId) {
        return ResultUtil.success("查询调用接口成功"+userId);
    }

    @PostMapping(value = "/savePort")
    public String savePort() {
        return "添加成功";
    }

//    public String testBlockHandler(BlockException blockException) {
//        return "自定义流控："+ blockException;
//    }

    @GetMapping(value = "/test")
    public ResultUtil<List<FlowInstanceEntity>> list() {
        List<FlowInstanceEntity> list = flowInstanceService.list();
        return ResultUtil.success(list);
    }


}
