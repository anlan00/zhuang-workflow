package com.zhuang.workflow.impl.handler;

import java.util.ArrayList;
import java.util.List;

import com.zhuang.workflow.model.UserInfo;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.NextTaskUsersHandler;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.enums.ProcessMainVariableNames;
import com.zhuang.workflow.service.UserManagementService;

public class CreateUserHandler implements NextTaskUsersHandler {

	@Autowired
	UserManagementService userManagementService;
	
	@Autowired
	ProcessInstanceManager processInstanceManager;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	public List<UserInfo> execute(WorkflowEngineContext workflowEngineContext) {

		List<UserInfo> result = new ArrayList<UserInfo>();

		UserInfo userInfo = new UserInfo();

		Task task = taskService.createTaskQuery().taskId(workflowEngineContext.getTaskId()).singleResult();
		
		Object objCreateUserId = runtimeService.getVariable(task.getExecutionId(), ProcessMainVariableNames.PROC_CREATE_USERID);

		userInfo = userManagementService.getUser(objCreateUserId.toString());

		result.add(userInfo);

		return result;
	}

}
