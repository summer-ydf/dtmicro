package com.cms.workflow.service;

import com.cms.common.tool.result.ResultUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ydf Created by 2021/12/2 15:18
 */
public interface WorkFlowService {

    void getProcessDiagram(HttpServletResponse httpServletResponse, String procInstId);

    ResultUtil<?> deleteDeployment(String deploymentId);

    ResultUtil<?> startProcess(String deploymentId);
}
