package com.cms.manage.service.impl;

import com.api.workflow.feign.WorkflowFeignService;
import com.cms.manage.service.WorkFlowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ydf Created by 2021/11/23 15:42
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    @Resource
    private WorkflowFeignService workflowFeignService;

    @Override
    public String getTest() {
        return workflowFeignService.getFlowPort();
    }
}
