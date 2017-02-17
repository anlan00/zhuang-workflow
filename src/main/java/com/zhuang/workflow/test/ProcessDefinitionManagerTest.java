package com.zhuang.workflow.test;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

import com.zhuang.workflow.WorkflowBeansFactory;

public class ProcessDefinitionManagerTest {

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
	
	@Test
	public void testGetProcessDefinitionList()
	{
		List<ProcessDefinition> processDefinitions=WorkflowBeansFactory.getProcessDefinitionManager().getProcessDefinitionList();
		for (ProcessDefinition processDefinition : processDefinitions) {
			System.out.println(processDefinition.getKey()+"|"+processDefinition.getName());
		}
		
		System.out.println("success!");
	}
}
