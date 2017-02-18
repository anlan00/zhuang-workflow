package com.zhuang.workflow.models;

import java.util.Date;

import org.junit.Test;

public class FlowInfoModel {
	
	private String taskId;
	
	private String businessKey;
	
	private String title;
	
	private String createUser;
	
	private String createUserId;
	
	private Date createTime;
	
	private String currentActivityName;
	
	private String type;

	private String defKey;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String applyUser) {
		this.createUser = applyUser;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String applyUserId) {
		this.createUserId = applyUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCurrentActivityName() {
		return currentActivityName;
	}

	public void setCurrentActivityName(String currentActivityName) {
		this.currentActivityName = currentActivityName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getDefKey() {
		return defKey;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}

	@Test
	public String toString() {
		return "taskId:"+taskId+"|"
				+"title:"+title+"|"
				+"type:"+type+"|"
				+"currentActivityName:"+currentActivityName+"|"
				+"applyUser:"+createUser+"|"
				+"applyUserId:"+createUserId+"|"
				+"applyTime:"+createTime+"|";
	}
}
