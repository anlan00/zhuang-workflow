package com.zhuang.workflow;

import java.util.List;

import com.zhuang.workflow.model.UserInfoModel;

public interface NextTaskUsersHandler {
	
	public List<UserInfoModel> execute(WorkflowEngineContext workflowEngineContext);
	
}
