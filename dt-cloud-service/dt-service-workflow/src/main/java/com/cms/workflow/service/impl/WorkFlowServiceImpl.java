package com.cms.workflow.service.impl;

import com.cms.common.tool.result.ResultUtil;
import com.cms.workflow.service.WorkFlowService;
import lombok.extern.apachecommons.CommonsLog;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ydf Created by 2021/12/2 15:18
 */
@CommonsLog
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

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

    @Override
    public void getProcessDiagram(HttpServletResponse response, String procInstId) {
        InputStream inputStream = createImgInputStream(procInstId);
        OutputStream outputStream = null;
        byte[] buf = new byte[1024];
        int length = 0;
        try {
            outputStream = response.getOutputStream();
            while ((length = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, length);
            }
        } catch (IOException e) {
            log.error("操作异常",e);
        } finally {
            assert outputStream != null;
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除流程定义
     * @param deploymentId 部署流程ID
     * @return 返回
     */
    @Override
    public ResultUtil<?> deleteDeployment(String deploymentId) {
        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式,如果该流程定义已有流程实例启动则删除时出错
        try {
            repositoryService.deleteDeployment(deploymentId, false);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("该流程定义已有流程实例启动");
        }
        return ResultUtil.success();
    }

    /**
     * 启动流程
     * @param deploymentId 部署流程ID
     * @return 返回
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> startProcess(String deploymentId) {
        // 启动流程实例，根据ACT_RE_PROCDEF表中流程定义的ID或者KEY
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinition.getKey());
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        log.info("流程实例ID："+processInstance.getId());
        log.info("流程定义ID："+processInstance.getProcessDefinitionId());
        // 流程实例ID：0fc68490-abf8-11ec-8d5e-005056c00008
        // 流程定义ID：process_test:1:b2a9b229-abf7-11ec-9750-005056c00008
        return ResultUtil.success(processInstance.getId());
    }


    /**
     * 生成流程图片流
     * @param processInstanceId 流程实例ID
     * @return 返回输入流
     */
    public InputStream createImgInputStream(String processInstanceId) {
        String processDefinitionId = null;
        // 判断流程实例是否已经结束
        boolean finished = this.isFinished(processInstanceId);
        if (finished) {
            // 获取历史流程实例
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        } else {
            // 获取当前活动状态的流程实例
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }
        // 设置高亮节点
        List<String> highLightActivitis = new ArrayList<>();
        List<String> highLightFlows = new ArrayList<>();
        List<HistoricActivityInstance> highLightActivitiList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        for(HistoricActivityInstance tempActivity : highLightActivitiList) {
            highLightActivitis.add(tempActivity.getActivityId());
        }
        // 获取BPMN模型对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration engineConfiguration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engineConfiguration.getProcessDiagramGenerator();
        return diagramGenerator.generateDiagram(bpmnModel, "png", highLightActivitis, highLightFlows,
                engineConfiguration.getActivityFontName(),
                engineConfiguration.getLabelFontName(),
                engineConfiguration.getAnnotationFontName(),
                engineConfiguration.getClassLoader(), 1.0, true);
    }

    /**
     * 判断流程实例是否已经结束
     * @param processInstanceId 流程实例ID
     * @return 返回true/false
     */
    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId).count() > 0;
    }


}
