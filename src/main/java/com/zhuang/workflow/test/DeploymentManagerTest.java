package com.zhuang.workflow.test;

import org.junit.Test;

import com.zhuang.workflow.WorkflowBeansFactory;

public class DeploymentManagerTest {

	@Test
	public void testDeployByClasspathResource() {

		WorkflowBeansFactory.getDeploymentManager().deployByClasspathResource("test02.bpmn", "test02");

		
		System.out.println("success!");
		
	}

}
