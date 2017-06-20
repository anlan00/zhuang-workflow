package com.zhuang.workflow;

import com.zhuang.workflow.activiti.DeploymentManager;
import com.zhuang.workflow.activiti.ProcessDefinitionManager;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.activiti.ProcessVariablesManager;
import com.zhuang.workflow.activiti.UserTaskManager;
import com.zhuang.workflow.utils.ApplicationContextUtil;

public class WorkflowBeansFactory {

	public static WorkflowEngine getWorkflowEngine() {
		return ApplicationContextUtil.GetApplicationContext().getBean("workflowEngine", WorkflowEngine.class);
	}

	public static WorkflowDeployment getWorkflowDeployment() {
		return ApplicationContextUtil.GetApplicationContext().getBean("workflowDeployment", WorkflowDeployment.class);
	}

	public static DeploymentManager getDeploymentManager() {
		return ApplicationContextUtil.GetApplicationContext().getBean("deploymentManager", DeploymentManager.class);

	}

	public static ProcessDefinitionManager getProcessDefinitionManager() {
		return ApplicationContextUtil.GetApplicationContext().getBean("processDefinitionManager",
				ProcessDefinitionManager.class);

	}

	public static WorkflowQueryManager getWorkflowQueryManager() {
		return ApplicationContextUtil.GetApplicationContext().getBean("workflowQueryManager",
				WorkflowQueryManager.class);
	}

	public static ProcessVariablesManager getProcessVariablesManager() {
		return ApplicationContextUtil.GetApplicationContext().getBean("processVariablesManager",
				ProcessVariablesManager.class);
	}

	public static UserTaskManager getUserTaskManager() {
		return ApplicationContextUtil.GetApplicationContext().getBean("userTaskManager", UserTaskManager.class);
	}

	public static ProcessInstanceManager getProcessInstanceManager() {
		return ApplicationContextUtil.GetApplicationContext().getBean("processInstanceManager",
				ProcessInstanceManager.class);
	}

}
