package com.zhuang.workflow.models;

import java.util.List;

public class NextTaskInfoModel {
	
	private String taskKey;

	private String taskName;

	private List<UserInfoModel> users;

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskName() {
		
		return taskName;
	}

	public void setTaskName(String taskName) {
		
		if(taskKey.equals("_endTask_"))
		{
			this.taskName = "结束";
		}else
		{
			this.taskName = taskName;
		}
	}

	public List<UserInfoModel> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfoModel> users) {
		this.users = users;
	}
	
	
}
