package com.zhuang.workflow.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.zhuang.workflow.WorkflowBeansFactory;
import com.zhuang.workflow.activiti.ProcessMainVariableNames;
import com.zhuang.workflow.models.NextTaskInfoModel;
import com.zhuang.workflow.models.UserInfoModel;

public class ActivitiWorkflowEngineTest {
	
	@Test
	public void testStartNew() {

		Map<String, Object> formData=new HashMap<String, Object>();
	/*	formData.put("env:env1111", 1111);
		formData.put("env:env2222", 1111);*/
		formData.put("env:"+ProcessMainVariableNames.PROC_TITLE,"bbbbbbbbbbbb");
		System.out.println(WorkflowBeansFactory.getWorkflowEngine().startNew("test01", "user1","f001",formData));


		System.out.println("success!");
		
	}

	@Test
	public void testSubmit() {

		List<String> users = new ArrayList<String>();
		users.add("zwb2");
	/*	users.add("z002");
		users.add("z003");*/
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("env_amount", 1189);
		map.put("env_choice", "提交");
		WorkflowBeansFactory.getWorkflowEngine().submit("325007",users, "同意", map);

		System.out.println("success!");

	}

	@Test
	public void testDelete() {

		List<String> users = new ArrayList<String>();
		users.add("user3");
	/*	users.add("z002");
		users.add("z003");*/
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("env:amount", 89);
		WorkflowBeansFactory.getWorkflowEngine().delete("315007", "删除", map);

		System.out.println("success!");

	}
		
	@Test
	public void testRetrieveNextTaskUsers() {

		Map<String, Object> formData = new HashMap<String, Object>();
		formData.put("env_amount", 11);
		NextTaskInfoModel nextTaskInfoModel = WorkflowBeansFactory.getWorkflowEngine().retrieveNextTaskInfo("350133", formData);

		for (UserInfoModel userInfoModel : nextTaskInfoModel.getUsers()) {
			System.out.println(userInfoModel.toString());
		}
		
		System.out.println("success!");
	}

	@Test
	public void testRetrieveFormData() {
		
		Map<String, Object> formData = WorkflowBeansFactory.getWorkflowEngine().retrieveFormData("237509");
		for (Entry entry : formData.entrySet()) {
			System.out.println(entry.getValue());
		}
		
		System.out.println("success!");

	}
}
