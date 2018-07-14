package com.zhuang.workflow;

import com.zhuang.workflow.activiti.DeploymentManager;
import com.zhuang.workflow.activiti.ProcessDefinitionManager;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.activiti.ProcessVariablesManager;
import com.zhuang.workflow.activiti.UserTaskManager;
import com.zhuang.workflow.util.ApplicationContextUtils;

public class WorkflowBeansFactory {

	public static WorkflowEngine getWorkflowEngine() {
		return ApplicationContextUtils.getApplicationContext().getBean("workflowEngine", WorkflowEngine.class);
	}

	public static WorkflowDeployment getWorkflowDeployment() {
		return ApplicationContextUtils.getApplicationContext().getBean("workflowDeployment", WorkflowDeployment.class);
	}

	public static DeploymentManager getDeploymentManager() {
		return ApplicationContextUtils.getApplicationContext().getBean("deploymentManager", DeploymentManager.class);

	}

	public static ProcessDefinitionManager getProcessDefinitionManager() {
		return ApplicationContextUtils.getApplicationContext().getBean("processDefinitionManager",
				ProcessDefinitionManager.class);

	}

	public static WorkflowQueryManager getWorkflowQueryManager() {
		return ApplicationContextUtils.getApplicationContext().getBean("workflowQueryManager",
				WorkflowQueryManager.class);
	}

	public static ProcessVariablesManager getProcessVariablesManager() {
		return ApplicationContextUtils.getApplicationContext().getBean("processVariablesManager",
				ProcessVariablesManager.class);
	}

	public static UserTaskManager getUserTaskManager() {
		return ApplicationContextUtils.getApplicationContext().getBean("userTaskManager", UserTaskManager.class);
	}

	public static ProcessInstanceManager getProcessInstanceManager() {
		return ApplicationContextUtils.getApplicationContext().getBean("processInstanceManager",
				ProcessInstanceManager.class);
	}

}
