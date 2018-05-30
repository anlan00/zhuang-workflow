package com.zhuang.workflow;

import com.zhuang.workflow.activiti.DeploymentManager;
import com.zhuang.workflow.activiti.ProcessDefinitionManager;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.activiti.ProcessVariablesManager;
import com.zhuang.workflow.activiti.UserTaskManager;
import com.zhuang.workflow.util.ApplicationContextUtils;

public class WorkflowBeansFactory {

	public static WorkflowEngine getWorkflowEngine() {
		return ApplicationContextUtils.GetApplicationContext().getBean("workflowEngine", WorkflowEngine.class);
	}

	public static WorkflowDeployment getWorkflowDeployment() {
		return ApplicationContextUtils.GetApplicationContext().getBean("workflowDeployment", WorkflowDeployment.class);
	}

	public static DeploymentManager getDeploymentManager() {
		return ApplicationContextUtils.GetApplicationContext().getBean("deploymentManager", DeploymentManager.class);

	}

	public static ProcessDefinitionManager getProcessDefinitionManager() {
		return ApplicationContextUtils.GetApplicationContext().getBean("processDefinitionManager",
				ProcessDefinitionManager.class);

	}

	public static WorkflowQueryManager getWorkflowQueryManager() {
		return ApplicationContextUtils.GetApplicationContext().getBean("workflowQueryManager",
				WorkflowQueryManager.class);
	}

	public static ProcessVariablesManager getProcessVariablesManager() {
		return ApplicationContextUtils.GetApplicationContext().getBean("processVariablesManager",
				ProcessVariablesManager.class);
	}

	public static UserTaskManager getUserTaskManager() {
		return ApplicationContextUtils.GetApplicationContext().getBean("userTaskManager", UserTaskManager.class);
	}

	public static ProcessInstanceManager getProcessInstanceManager() {
		return ApplicationContextUtils.GetApplicationContext().getBean("processInstanceManager",
				ProcessInstanceManager.class);
	}

}
