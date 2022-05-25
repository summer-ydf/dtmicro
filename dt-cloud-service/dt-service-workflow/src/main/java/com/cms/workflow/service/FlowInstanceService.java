package com.cms.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.workflow.entity.FlowInstanceEntity;

/**
 * @author DT辰白 Created by 2021/12/1 11:33
 */
public interface FlowInstanceService extends IService<FlowInstanceEntity> {

    ResultUtil<?> createDeployment();
}
