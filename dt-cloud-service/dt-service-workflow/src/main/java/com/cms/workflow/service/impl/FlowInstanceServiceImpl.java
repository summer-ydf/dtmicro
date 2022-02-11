package com.cms.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.result.ResultUtil;
import com.cms.workflow.entity.FlowInstanceEntity;
import com.cms.workflow.mapper.FlowInstanceMapper;
import com.cms.workflow.service.FlowInstanceService;
import lombok.extern.apachecommons.CommonsLog;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ydf Created by 2021/12/1 11:34
 */
@CommonsLog
@Service
public class FlowInstanceServiceImpl extends ServiceImpl<FlowInstanceMapper, FlowInstanceEntity> implements FlowInstanceService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> createDeployment() {
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程部署")
                .addClasspathResource("flow/MyProcess.bpmn20.xml")
                .deploy();

        log.info("部署名称："+deployment.getName());
        log.info("部署ID："+deployment.getId());
        log.info("部署时间："+deployment.getDeploymentTime());
        return ResultUtil.success(deployment);
    }
}
