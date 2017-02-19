package com.zhuang.workflow.test;

import java.util.List;

import com.zhuang.workflow.WorkflowActionListener;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.models.UserInfoModel;
import com.zhuang.workflow.models.WorkflowChoiceOptions;

public class Test01ActionListener implements WorkflowActionListener {

	public void beforSubmit(WorkflowEngineContext context) {

		System.out.println("beforSubmit--------------------"+context.getChoice());
	}

	public void afterSubmit(WorkflowEngineContext context) {

		System.out.println("afterSubmit--------------------"+context.getChoice());
	}


	public void beforBack(WorkflowEngineContext context) {
		System.out.println("beforBack--------------------");
		
	}

	public void afterBack(WorkflowEngineContext context) {
		System.out.println("afterBack--------------------");
		
	}

	public void beforReject(WorkflowEngineContext context) {
		System.out.println("beforReject--------------------");
		
	}

	public void afterReject(WorkflowEngineContext context) {
		System.out.println("afterReject--------------------");
		
	}

	public void onSave(WorkflowEngineContext context) {
		System.out.println("onSave--------------------");
		
	}

	public void onRetrieveNextTaskUsers(List<UserInfoModel> nextTaskUsers, WorkflowEngineContext context) {
		System.out.println("onRetrieveNextTaskUsers--------------------");
		
	}

	public void onRetrieveFormData(WorkflowEngineContext context) {
		System.out.println("onRetrieveFormData--------------------");
		System.out.println("context.getCurrentTaskDef()"+context.getCurrentTaskDef());
		if(context.getCurrentTaskDef().equals("mgr1") || context.getCurrentTaskDef().equals("mgr2"))
		{
			context.getFormData().put("toolbar_back", true);
		}
	}

	public void beforDelete(WorkflowEngineContext context) {
		System.out.println("beforDelete--------------------");
	}

	public void afterDelete(WorkflowEngineContext context) {
		System.out.println("afterDelete--------------------");		
	}
}
