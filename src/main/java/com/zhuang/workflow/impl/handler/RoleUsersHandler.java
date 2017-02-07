package com.zhuang.workflow.impl.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.NextTaskUsersHandler;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.models.UserInfoModel;
import com.zhuang.workflow.services.UserManagementService;

public class RoleUsersHandler implements NextTaskUsersHandler {

	@Autowired
	private UserManagementService userManagementService;
	
	public List<UserInfoModel> execute(WorkflowEngineContext workflowEngineContext) {

		List<UserInfoModel> result = new ArrayList<UserInfoModel>();
		
		String roleId = workflowEngineContext.getComment();

		if (roleId != null) {
			result = userManagementService.getUsersByRoleId(roleId);
		}
		
		return result;
	}

}
