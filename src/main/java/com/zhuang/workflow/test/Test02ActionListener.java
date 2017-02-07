package com.zhuang.workflow.test;

import java.util.List;

import com.zhuang.workflow.WorkflowActionListener;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.models.UserInfoModel;

public class Test02ActionListener implements WorkflowActionListener {

	public void beforSubmit(WorkflowEngineContext context) {

		System.out.println("beforSubmit.......");
	}

	public void afterSubmit(WorkflowEngineContext context) {

		System.out.println("afterSubmit.......");
	}


	public void beforBack(WorkflowEngineContext context) {
		// TODO Auto-generated method stub
		
	}

	public void afterBack(WorkflowEngineContext context) {
		// TODO Auto-generated method stub
		
	}

	public void beforReject(WorkflowEngineContext context) {
		// TODO Auto-generated method stub
		
	}

	public void afterReject(WorkflowEngineContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onSave(WorkflowEngineContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onRetrieveNextTaskUsers(List<UserInfoModel> nextTaskUsers, WorkflowEngineContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onRetrieveFormData(WorkflowEngineContext context) {
		// TODO Auto-generated method stub
		
	}
}
