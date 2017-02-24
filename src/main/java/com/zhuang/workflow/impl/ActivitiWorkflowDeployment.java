package com.zhuang.workflow.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.WorkflowDeployment;
import com.zhuang.workflow.activiti.DeploymentManager;

public class ActivitiWorkflowDeployment implements WorkflowDeployment{

	@Autowired
	private DeploymentManager deploymentManager;
	
	public void deployByInputStream(String resourceName, InputStream inputStream) {
		deploymentManager.deployByInputStream(resourceName, inputStream);
	}

}
