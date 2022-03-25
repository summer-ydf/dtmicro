package com.cms.workflow.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.workflow.entity.FlowInstanceEntity;
import com.cms.workflow.entity.FlowProcessEntity;
import com.cms.workflow.service.FlowInstanceService;
import com.cms.workflow.service.WorkFlowService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.dmn.engine.impl.deployer.ParsedDeployment;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * @author ydf Created by 2021/11/23 16:21
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/flow")
public class WorkFlowController {

    @Autowired
    private FlowInstanceService flowInstanceService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private RepositoryService repositoryService;

    @GetMapping(value = "/getPort/{userId}")
//    @SentinelResource(value = "/getPort/{userId}",blockHandler = "testBlockHandler")
    public ResultUtil<?> getFlowPort(@PathVariable String userId) {
        return ResultUtil.success("查询调用接口成功"+userId);
    }

    @PostMapping(value = "/savePort")
    public String savePort() {
        return "添加成功";
    }

    @GetMapping(value = "/test")
    public ResultUtil<List<FlowInstanceEntity>> list() {
        List<FlowInstanceEntity> list = flowInstanceService.list();
        return ResultUtil.success(list);
    }

    @PostMapping(value = "/createDeployment")
    public ResultUtil<?> createDeployment() {
        return flowInstanceService.createDeployment();
    }

    @ApiOperation(value = "获取流程运行实例")
    @GetMapping(value = "/get_process_diagram")
    public void getProcessDiagram(HttpServletResponse httpServletResponse, String procInstId) {
        workFlowService.getProcessDiagram(httpServletResponse,procInstId);
    }

    @ApiOperation(value = "部署流程定义")
    @PostMapping(value = "/save_deployment")
    public ResultUtil<?> saveDeployment(@RequestParam("file") MultipartFile multipartFile, String id, String name, String category) throws IOException {
        FlowProcessEntity flowProcess = new FlowProcessEntity();
        flowProcess.setId(id);
        flowProcess.setName(name);
        flowProcess.setCategory(category);
        String resourceName = multipartFile.getOriginalFilename();
        // 后缀名不能写错，否则会出现ACT_RE_PROCDEF表无内容插入，当部署流程的时候，调用deploy()方法，源码里面有!this.isBpmnResource(resource.getName())判断，资源后缀不含.bpmn20.xml时，就不插入ACT_RE_PROCDEF表内容
        if (null != resourceName && !resourceName.endsWith(".bpmn20.xml")){
            resourceName = resourceName + ".bpmn20.xml";
        }
        System.out.println("resourceName->>>"+resourceName);
        // 部署流程，涉及以下三张表的数据添加，删除流程定义时，也会删除以下三张关联表
        // 部署表：ACT_RE_DEPLOYMENT
        // 流程定义表：ACT_RE_PROCDEF
        // 流程定义和流程资源表：ACT_GE_BYTEARRAY
        // 注意：二次部署的时候，已部署的流程定义，当流程标识key相同时，会在ACT_RE_PROCDEF表进行版本叠加。
        Deployment deployStream = repositoryService.createDeployment()
                .name(name)
                .category(category)
                .addInputStream(resourceName, multipartFile.getInputStream())
                .deploy();
        return ResultUtil.success(deployStream);
    }

    @ApiOperation(value = "删除流程定义")
    @PostMapping(value = "delete_deployment/{deploymentId}")
    public ResultUtil<?> deleteDeployment(@PathVariable String deploymentId) {
        return workFlowService.deleteDeployment(deploymentId);
    }

    @ApiOperation(value = "启动流程")
    @PostMapping(value = "start_process/{deploymentId}")
    public ResultUtil<?> startProcess(@PathVariable String deploymentId) {
        return workFlowService.startProcess(deploymentId);
    }

    @GetMapping(value = "/listFlowModel")
    public ResultUtil<?> listFlowModel() {
        List<Model> processDefinitionList = repositoryService.createModelQuery().list();
        return ResultUtil.success(processDefinitionList);
    }

}
