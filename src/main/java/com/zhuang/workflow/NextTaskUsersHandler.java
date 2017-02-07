package com.zhuang.workflow;

import java.util.List;

import com.zhuang.workflow.models.UserInfoModel;

public interface NextTaskUsersHandler {
	
	public List<UserInfoModel> execute(WorkflowEngineContext workflowEngineContext);
	
}
