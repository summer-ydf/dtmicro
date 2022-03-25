package com.cms.workflow.service;

import com.cms.common.tool.result.ResultUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ydf Created by 2021/12/2 15:18
 */
public interface WorkFlowService {

    /**
     * 获取流程图
     * @param httpServletResponse 返回响应
     * @param processId 实例ID
     */
    void genProcessDiagram(HttpServletResponse httpServletResponse, String processId);

    ResultUtil<?> deleteDeployment(String deploymentId);
}
