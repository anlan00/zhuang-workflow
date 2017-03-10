package com.zhuang.workflow.models;

public class TaskDefModel {
	
	private String key;
	
	private String name;

	private String assignee;
	
	private Boolean isCountersign;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Boolean getIsCountersign() {
		return isCountersign;
	}

	public void setIsCountersign(Boolean isCountersign) {
		this.isCountersign = isCountersign;
	}
	
}
