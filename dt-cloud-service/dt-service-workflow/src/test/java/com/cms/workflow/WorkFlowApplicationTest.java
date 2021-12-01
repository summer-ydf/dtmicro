package com.cms.workflow;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
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
        String deploymentId = "8f59e523-527f-11ec-a033-005056c00008";
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
        String deploymentId = "8f59e523-527f-11ec-a033-005056c00008";
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
                .taskAssignee("张三")
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
        String taskId = "b1771b98-527f-11ec-9cbc-005056c00008";
        processEngine.getTaskService().complete(taskId);
        log.info("完成任务======================");
    }

    /**
     * 根据流程实例ID 查看历史任务
     */
    @Test
    void findHistory() {
        // 流程定义ID
        String processDefinitionId = "myProcess:3:8f87d2f6-527f-11ec-a033-005056c00008";
        // 流程实例ID
        String processInstanceId = "b1745c73-527f-11ec-9cbc-005056c00008";
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().asc()
                        .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println("===========历史节点===========");
            log.info("活动节点ID："+activity.getActivityId());
            log.info("活动节点名称："+activity.getActivityName());
            log.info("花费毫秒："+activity.getDurationInMillis() + " milliseconds");
        }
    }

}
