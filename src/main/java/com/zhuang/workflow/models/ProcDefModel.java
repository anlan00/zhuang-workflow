package com.zhuang.workflow.models;

public class ProcDefModel {
	
	private String key;
	
	private String name;

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
	
	@Override
	public String toString() {
		
		return "key"+getKey()+"|"+"name"+getName();
		
	}
	
}
