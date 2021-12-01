package com.cms.workflow;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ydf Created by 2021/12/1 14:28
 */
@SpringBootTest
@Slf4j
public class WorkFlowApplicationTest {

    @Resource
    private ProcessEngine processEngine;

    /**
     * 部署方式一：流程资源xml部署
     */
    @Test
    void deployFlow() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程部署")
                .addClasspathResource("flow/MyProcess.bpmn20.xml")
                .deploy();

        log.info("部署名称："+deployment.getName());
        log.info("部署ID："+deployment.getId());
        log.info("部署时间："+deployment.getDeploymentTime());
    }

    /**
     * 通过部署ID获取流程定义
     */
    @Test
    void getProcessDefinition() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String deploymentId = "74ccf73e-527a-11ec-a057-005056c00008";
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
        log.info("流程定义："+processDefinition);
    }

    /**
     * 根据流程定义启动流程
     */
    @Test
    void startProcessInstanceByDefinitionId() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String deploymentId = "74ccf73e-527a-11ec-a057-005056c00008";
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
        log.info("流程定义ID："+processDefinition.getId());
        log.info("==========================启动流程===========================");
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        log.info("流程实例："+processInstance);
        log.info("流程实例ID："+processInstance.getId());
        log.info("流程定义ID："+processInstance.getProcessDefinitionId());
    }

    /**
     * 查询个人任务
     */
    @Test
    void findMyTask() {
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("李四")
                .orderByTaskCreateTime().asc()
                .list();
        log.info("个人任务："+tasks.size());
        for (Task task : tasks) {
            log.info("任务ID："+task.getId());
            log.info("任务名称："+task.getName());
            log.info("任务创建时间："+task.getCreateTime());
            log.info("任务办理人："+task.getAssignee());
            log.info("流程实例ID："+task.getProcessInstanceId());
            log.info("执行对象ID："+task.getExecutionId());
            log.info("流程定义ID："+task.getProcessDefinitionId());
            log.info("=========================================");
        }
    }

    /**
     * 根据任务ID完成个人任务
     */
    @Test
    void complete() {
        // 根据任务ID完成个人任务
        String taskId = "8da41416-527e-11ec-b239-005056c00008";
        processEngine.getTaskService().complete(taskId);
        log.info("完成任务======================");
    }

}
