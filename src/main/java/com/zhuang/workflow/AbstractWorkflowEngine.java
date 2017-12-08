package com.zhuang.workflow;

import java.util.Map;

/**
 * 工作流引擎抽象基类
 * 
 * @author zwb
 *
 */
public abstract class AbstractWorkflowEngine implements WorkflowEngine {

	/**
	 * 工作流动作监听器集合
	 */
	protected Map<String, WorkflowActionListener> workflowActionListeners;

	protected Map<String, NextTaskUsersHandler> nextTaskUsersHandlers;

	public Map<String, WorkflowActionListener> getWorkflowActionListeners() {

		return workflowActionListeners;
	}

	public void setWorkflowActionListeners(Map<String, WorkflowActionListener> workflowActionListeners) {
		this.workflowActionListeners = workflowActionListeners;
	}

	public Map<String, NextTaskUsersHandler> getNextTaskUsersHandlers() {
		return nextTaskUsersHandlers;
	}

	public void setNextTaskUsersHandlers(Map<String, NextTaskUsersHandler> nextTaskUsersHandlers) {
		this.nextTaskUsersHandlers = nextTaskUsersHandlers;
	}

}
