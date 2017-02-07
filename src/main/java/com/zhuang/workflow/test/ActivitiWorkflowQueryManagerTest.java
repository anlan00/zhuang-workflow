package com.zhuang.workflow.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.zhuang.workflow.WorkflowBeansFactory;
import com.zhuang.workflow.activiti.ProcessMainVariableNames;
import com.zhuang.workflow.commons.PageModel;
import com.zhuang.workflow.models.FlowInfoModel;

public class ActivitiWorkflowQueryManagerTest {
	
	
	@Test
	public void test()
	{
	
	}
	
	
	@Test
	public  void testGetMyTodoListPage() {
		
		Map<String, Object> map =new HashMap<String, Object>();
		//map.put(ProcessMainVariableNames.PROC_TITLE, "%20170112");
		PageModel<FlowInfoModel> pageModel = WorkflowBeansFactory.getWorkflowQueryManager()
				.getMyTodoListPage("user1",1, 10, map );
		
		System.out.println(pageModel.getList().size());
		System.out.println(pageModel.getTotalRows());
		System.out.println(pageModel.getPageStartRow());
		System.out.println(pageModel.getHasPreviousPage());
		System.out.println(pageModel.getHasNextPage());
		
		for (FlowInfoModel flowInfoModel : pageModel.getList()) {

			System.out.println(flowInfoModel); 	
		
		}
	
	}

	@Test
	public  void testGetMyDoneListPage() {
		
		System.out.println("testGetMyDoneListPage>>>>>>");
		
		Map<String, Object> map =new HashMap<String, Object>();
		//map.put(ProcessMainVariableNames.PROC_TITLE, "%20170112");
		PageModel<FlowInfoModel> pageModel = WorkflowBeansFactory.getWorkflowQueryManager()
				.getMyDoneListPage("zwb01",1, 10, map );
		
		System.out.println(pageModel.getList().size());
		System.out.println(pageModel.getTotalRows());
		System.out.println(pageModel.getPageStartRow());
		System.out.println(pageModel.getHasPreviousPage());
		System.out.println(pageModel.getHasNextPage());
		
		for (FlowInfoModel flowInfoModel : pageModel.getList()) {

			System.out.println(flowInfoModel); 	
		
		}
	
		System.out.println("<<<<<<<testGetMyDoneListPage");
		
	}

}
