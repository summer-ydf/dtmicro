package com.cms.manage.service.impl;

import com.api.workflow.feign.WorkflowFeignService;
import com.cms.common.result.ResultUtil;
import com.cms.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2021/11/23 16:32
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private WorkflowFeignService workflowFeignService;

    @Override
    public ResultUtil<?> getTest(String userId) {
        System.out.println("开启远程服务调用->>>");
        // 远程服务调用
        return workflowFeignService.getFlowPort(userId);
    }

    @Override
    public String save() {
        System.out.println("开启远程服务调用->>>");
        // 远程服务调用
        return workflowFeignService.savePort();
    }
}
