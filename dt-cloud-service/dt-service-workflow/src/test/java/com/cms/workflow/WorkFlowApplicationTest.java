package com.cms.workflow;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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

}
