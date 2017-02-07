package com.zhuang.workflow.test;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.junit.Test;

import com.zhuang.workflow.WorkflowBeansFactory;

public class ProcessDefinitionManager {

	@Test
	public void testGetProcessDefinitionEntityByTaskId() {
		
		WorkflowBeansFactory.getProcessDefinitionManager().getProcessDefinitionEntityByTaskId("237509");
		
	}
	
	@Test
	public void testGetProcessDefinitionEntityByKey() {
		
		ProcessDefinitionEntity processDefinitionEntity = WorkflowBeansFactory.getProcessDefinitionManager().getProcessDefinitionEntityByKey("test01");
		System.out.println(processDefinitionEntity.getKey());
		System.out.println(processDefinitionEntity.getName());
		
	}
	

	@Test
	public void testIsFirstTask() {
		
		System.out.println(WorkflowBeansFactory.getProcessDefinitionManager().isFirstTask("212506"));
		
	}
	
}
