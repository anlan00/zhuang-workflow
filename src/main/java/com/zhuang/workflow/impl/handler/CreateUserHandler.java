package com.zhuang.workflow.impl.handler;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.NextTaskUsersHandler;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.activiti.ProcessMainVariableNames;
import com.zhuang.workflow.models.UserInfoModel;
import com.zhuang.workflow.services.UserManagementService;

public class CreateUserHandler implements NextTaskUsersHandler {

	@Autowired
	UserManagementService userManagementService;
	
	@Autowired
	ProcessInstanceManager processInstanceManager;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	public List<UserInfoModel> execute(WorkflowEngineContext workflowEngineContext) {

		List<UserInfoModel> result = new ArrayList<UserInfoModel>();

		UserInfoModel userInfoModel = new UserInfoModel();

		Task task = taskService.createTaskQuery().taskId(workflowEngineContext.getTaskId()).singleResult();
		
		Object objCreateUserId = runtimeService.getVariable(task.getExecutionId(), ProcessMainVariableNames.PROC_CREATE_USERID);

		userInfoModel = userManagementService.getUser(objCreateUserId.toString());

		result.add(userInfoModel);

		return result;
	}

}
