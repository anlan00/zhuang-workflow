package com.zhuang.workflow.test;
import org.junit.Test;

import com.zhuang.workflow.WorkflowBeansFactory;


public class ProcessInstanceManagerTest {

	@Test
	public void testDeleteProcessInstanceByTaskId() {
		
		WorkflowBeansFactory.getProcessInstanceManager().deleteProcessInstanceByTaskId("282529");
		System.out.println("success!");
	}
	
}
