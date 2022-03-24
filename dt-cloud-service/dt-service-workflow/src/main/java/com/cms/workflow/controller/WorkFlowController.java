package com.cms.workflow.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.workflow.entity.FlowInstanceEntity;
import com.cms.workflow.service.FlowInstanceService;
import com.cms.workflow.service.WorkFlowService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.List;

/**
 * @author ydf Created by 2021/11/23 16:21
 */
@RestController
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

    @GetMapping(value = "/genProcessDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) {
        workFlowService.genProcessDiagram(httpServletResponse,processId);
    }

    private XMLInputFactory factory = XMLInputFactory.newInstance();
    private BpmnXMLConverter bpmnXMLConverter;

    @PostMapping(value = "/deployment")
    public ResultUtil<?> deployment() {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:bioc=\"http://bpmn.io/schema/bpmn/biocolor/1.0\" xmlns:flowable=\"http://flowable.org/bpmn\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"http://www.flowable.org/processdef\">\n" +
                "  <process id=\"process_sgcyjwze\" name=\"name_26gnn4i8\">\n" +
                "    <startEvent id=\"startNode1\" name=\"开始\">\n" +
                "      <outgoing>Flow_0bwug55</outgoing>\n" +
                "    </startEvent>\n" +
                "    <sequenceFlow id=\"Flow_0bwug55\" sourceRef=\"startNode1\" targetRef=\"Activity_0icoeyt\" />\n" +
                "    <userTask id=\"Activity_0icoeyt\" flowable:assignee=\"1\">\n" +
                "      <incoming>Flow_0bwug55</incoming>\n" +
                "      <outgoing>Flow_19baw3d</outgoing>\n" +
                "    </userTask>\n" +
                "    <endEvent id=\"Event_1hznzo3\">\n" +
                "      <incoming>Flow_19baw3d</incoming>\n" +
                "    </endEvent>\n" +
                "    <sequenceFlow id=\"Flow_19baw3d\" sourceRef=\"Activity_0icoeyt\" targetRef=\"Event_1hznzo3\" />\n" +
                "  </process>\n" +
                "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_flow\">\n" +
                "    <bpmndi:BPMNPlane id=\"BPMNPlane_flow\" bpmnElement=\"process_sgcyjwze\">\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0bwug55_di\" bpmnElement=\"Flow_0bwug55\">\n" +
                "        <di:waypoint x=\"-75\" y=\"230\" />\n" +
                "        <di:waypoint x=\"-20\" y=\"230\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_19baw3d_di\" bpmnElement=\"Flow_19baw3d\">\n" +
                "        <di:waypoint x=\"80\" y=\"230\" />\n" +
                "        <di:waypoint x=\"142\" y=\"230\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNShape id=\"BPMNShape_startNode1\" bpmnElement=\"startNode1\" bioc:stroke=\"\">\n" +
                "        <omgdc:Bounds x=\"-105\" y=\"215\" width=\"30\" height=\"30\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"-102\" y=\"252\" width=\"22\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1hotllm_di\" bpmnElement=\"Activity_0icoeyt\">\n" +
                "        <omgdc:Bounds x=\"-20\" y=\"190\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Event_1hznzo3_di\" bpmnElement=\"Event_1hznzo3\">\n" +
                "        <omgdc:Bounds x=\"142\" y=\"212\" width=\"36\" height=\"36\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "    </bpmndi:BPMNPlane>\n" +
                "  </bpmndi:BPMNDiagram>\n" +
                "</definitions>\n";
        XMLStreamReader streamReader = null;
        try {
            streamReader = factory.createXMLStreamReader(new StringReader(str));
        } catch (XMLStreamException e) {
            System.out.println("XML解析异常");
            e.printStackTrace();
        }
        System.out.println(streamReader);
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(streamReader);
        String id = bpmnModel.getMainProcess().getId();
        String name = bpmnModel.getMainProcess().getName();
        System.out.println(id);
        System.out.println(name);
//        Model model = repositoryService.newModel();
//        model.setKey("123456");
//        model.setName("请假流程模型");
//        model.setVersion(1);
//        model.setMetaInfo("{\"name\":\"工资扣款项目审核V2\",\"revision\":1,\"description\":\"工资扣款项目审核V2\"}");
//        model.setTenantId("ABC");
//        model.setCategory("A");
//        repositoryService.saveModel(model);

//        Model modelData = modelService.getModel(modelId);
//        byte[] bytes = modelService.getBpmnXML(modelData);
//        if(bytes==null){
//            res.put("error","模型数据为空，请先设计流程并成功保存，再进行发布。");
//            return res;
//        }
//        BpmnModel model = modelService.getBpmnModel(modelData);
//        if(model.getProcesses().size()==0){
//            res.put("error","数据模型不符要求，请至少设计一条主线流程。");
//            return res;
//        }
//        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
//        String processName = modelData.getName()+".bpmn20.xml";
//        Deployment deployment = repositoryService.createDeployment()
//                .name(modelData.getName())
//                .addBytes(processName,bpmnBytes)
//                .deploy();
        return ResultUtil.success();
    }

    @GetMapping(value = "/listFlowModel")
    public ResultUtil<?> listFlowModel() {
        List<Model> processDefinitionList = repositoryService.createModelQuery().list();
        return ResultUtil.success(processDefinitionList);
    }

}
