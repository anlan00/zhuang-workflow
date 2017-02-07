package com.zhuang.workflow.models;

import java.util.Date;

import org.junit.Test;

public class FlowInfoModel {
	
	private String taskId;
	
	private String businessKey;
	
	private String title;
	
	private String applyUser;
	
	private String applyUserId;
	
	private Date applyTime;
	
	private String currentActivityName;
	
	private String type;

	
	
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

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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
	
	@Test
	public String toString() {
		return "taskId:"+taskId+"|"
				+"title:"+title+"|"
				+"type:"+type+"|"
				+"currentActivityName:"+currentActivityName+"|"
				+"applyUser:"+applyUser+"|"
				+"applyUserId:"+applyUserId+"|"
				+"applyTime:"+applyTime+"|";
	}
}
