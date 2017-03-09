package com.zhuang.workflow.models;

public class TaskDefModel {
	
	private String key;
	
	private String name;

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

	public Boolean getIsCountersign() {
		return isCountersign;
	}

	public void setIsCountersign(Boolean isCountersign) {
		this.isCountersign = isCountersign;
	}
	
}
