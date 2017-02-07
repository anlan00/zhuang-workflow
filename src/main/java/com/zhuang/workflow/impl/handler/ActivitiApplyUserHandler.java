package com.zhuang.workflow.impl.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.NextTaskUsersHandler;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.models.UserInfoModel;
import com.zhuang.workflow.services.UserManagementService;

public class ActivitiApplyUserHandler implements NextTaskUsersHandler {

	@Autowired
	UserManagementService userManagementService;
	@Autowired
	ProcessInstanceManager processInstanceManager;
	
	public List<UserInfoModel> execute(WorkflowEngineContext workflowEngineContext) {
		List<UserInfoModel> result = new ArrayList<UserInfoModel>();

		UserInfoModel userInfoModel = new UserInfoModel();

		String applyUserId = processInstanceManager.getApplyUserId(workflowEngineContext.getTaskId());

		userInfoModel = userManagementService.getUser(applyUserId);

		result.add(userInfoModel);

		return result;
	}

}
