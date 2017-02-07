package com.zhuang.workflow.impl;

import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.WorkflowEngine;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.activiti.ProcessDefinitionManager;
import com.zhuang.workflow.models.TaskDefModel;
import com.zhuang.workflow.models.TaskInfoModel;

public class ActivitiWorkflowEngineContext extends WorkflowEngineContext {

	@Autowired
	private ProcessDefinitionManager processDefinitionManager;
	
	public ActivitiWorkflowEngineContext(WorkflowEngine workflowEngine) {
		super(workflowEngine);
	}
	
	@Override
	public TaskDefModel getCurrentTaskDef() {
		
		/*ActivitiWorkflowEngine activitiWorkflowEngine = (ActivitiWorkflowEngine) workflowEngine;
		Task task = activitiWorkflowEngine.getTaskService().createTaskQuery().taskId(currentTask.getId())
				.singleResult();
		
		currentTask.setName(task.getName());*/
		
		return currentTaskDef;
	}
	
	@Override	
	public TaskDefModel getNextTaskDef() {
		
		ActivitiWorkflowEngine activitiWorkflowEngine = (ActivitiWorkflowEngine) workflowEngine;
		
//		Map<String, Object> params=activitiWorkflowEngine.getEnvVarFromFormData(formData);
//		Task nextTask = processDefinitionManager.getNextTaskDefinition(currentTask.getId(), params);
		
		return nextTaskDef;
	}
}
