package com.activitimodeler.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Controller  
@Scope("prototype")   
@RequestMapping("/activitiModeler")
public class CreateModelerController {

	    
	    /**
	     * 创建模型
	     */
	    @RequestMapping("create")
	    public void create(HttpServletRequest request, HttpServletResponse response) {
	        try {
	        	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	        	 
	        	RepositoryService repositoryService = processEngine.getRepositoryService();
	        	 
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode editorNode = objectMapper.createObjectNode();
	            editorNode.put("id", "canvas");
	            editorNode.put("resourceId", "canvas");
	            ObjectNode stencilSetNode = objectMapper.createObjectNode();
	            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
	            editorNode.put("stencilset", stencilSetNode);
	            Model modelData = repositoryService.newModel();

	            ObjectNode modelObjectNode = objectMapper.createObjectNode();
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "activitimodel");
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
	            String description = "activitimodel---";
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
	            modelData.setMetaInfo(modelObjectNode.toString());
	            modelData.setName("activitimodel");
	            modelData.setKey("12313123");

	            //保存模型
	            repositoryService.saveModel(modelData);
	            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
	            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
	        } catch (Exception e) {
	            System.out.println("创建模型失败：");
	        }
	    }
}
