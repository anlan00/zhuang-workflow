package com.zhuang.workflow.models;

import java.util.Date;

public class DeploymentInfoModel {

	private String id;
	
	private String name;

	private String category;
	
	private Date deployTime;
	
	private String procDefName;
	
	private String procDefKey;

	private int procDefVersion;

	private String procDefDescription;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}

	public String getProcDefName() {
		return procDefName;
	}

	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	public int getProcDefVersion() {
		return procDefVersion;
	}

	public void setProcDefVersion(int procDefVersion) {
		this.procDefVersion = procDefVersion;
	}

	public String getProcDefDescription() {
		return procDefDescription;
	}

	public void setProcDefDescription(String procDefDescription) {
		this.procDefDescription = procDefDescription;
	}
	
	
}
