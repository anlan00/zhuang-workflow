package com.zhuang.workflow.activiti;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTaskManager {

	@Autowired
	TaskService taskService;

	public boolean canExecuteTask(String taskId, String userId) {
		TaskQuery taskQuery = taskService.createTaskQuery().taskId(taskId).taskCandidateOrAssigned(userId);

		long count = taskQuery.count();

		return count > 0 ? true : false;
	}

	public boolean isRunningTask(String taskId) {

		TaskQuery taskQuery = taskService.createTaskQuery().taskId(taskId);

		long count = taskQuery.count();

		return count > 0 ? true : false;
	}
}
