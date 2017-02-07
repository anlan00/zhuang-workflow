package com.zhuang.workflow.test;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.junit.Test;

public class ActivitiTest {

	@Test
	public void testProcessDefinitionEntity() {
		ProcessDefinitionEntity processDefinitionEntity =  (ProcessDefinitionEntity)ActivitiTestBean.getActivitiTestBean().getRepositoryService()
				.getProcessDefinition(ActivitiTestBean.getActivitiTestBean().getTaskService().createTaskQuery().taskId("70001").singleResult().getProcessDefinitionId());

		for(ActivityImpl activityImpl:processDefinitionEntity.getActivities())
		{
			System.out.println(activityImpl.getProperty("name"));
		}
	}
	
	
}
