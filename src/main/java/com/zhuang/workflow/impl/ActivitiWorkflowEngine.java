package com.zhuang.workflow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.or.ThreadGroupRenderer;
import org.aspectj.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.AbstractWorkflowEngine;
import com.zhuang.workflow.NextTaskUsersHandler;
import com.zhuang.workflow.WorkflowActionListener;
import com.zhuang.workflow.WorkflowEngineContext;
import com.zhuang.workflow.activiti.ProcessDefinitionManager;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.activiti.ProcessMainVariableNames;
import com.zhuang.workflow.activiti.ProcessVariablesManager;
import com.zhuang.workflow.activiti.UserTaskManager;
import com.zhuang.workflow.exceptions.HandlerNotFoundException;
import com.zhuang.workflow.models.NextTaskInfoModel;
import com.zhuang.workflow.models.TaskDefModel;
import com.zhuang.workflow.models.TaskInfoModel;
import com.zhuang.workflow.models.UserInfoModel;
import com.zhuang.workflow.models.WorkflowChoiceOptions;
import com.zhuang.workflow.services.UserManagementService;

public class ActivitiWorkflowEngine extends AbstractWorkflowEngine {

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private FormService formService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private ProcessDefinitionManager processDefinitionManager;

	@Autowired
	private ProcessVariablesManager processVariablesManager;

	@Autowired
	private ProcessInstanceManager processInstanceManager;
	
	@Autowired
	private UserTaskManager userTaskManager;
	
	@Autowired
	private UserManagementService userManagementService;
	
	public static final String ACTIVITI_ENV_VAR_KEY_PREFIX = "env_";

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public FormService getFormService() {
		return formService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public ProcessDefinitionManager getProcessDefinitionManager() {
		return processDefinitionManager;
	}

	public void test() {
		/*
		 * for (Execution execution :
		 * runtimeService.createExecutionQuery().list()) {
		 * runtimeService.deleteProcessInstance(execution.getProcessInstanceId()
		 * , ""); }
		 */
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(
						taskService.createTaskQuery().taskId("75003").singleResult().getProcessDefinitionId());

		for (ActivityImpl activityImpl : processDefinitionEntity.getActivities()) {
			System.out.println(activityImpl.getProperty("name"));
		}
	}

	public String startNew(String processDefinitionKey, String userId, String businessKey,
			Map<String, Object> formData) {

		formData = ensureFormDataNotNull(formData);

		Map<String, Object> envVariables = getEnvVarFromFormData(formData);

		identityService.setAuthenticatedUserId(userId);

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).latestVersion().singleResult();

		envVariables.put(ProcessMainVariableNames.PROC_DEF_KEY, processDefinition.getKey());
		
		envVariables.put(ProcessMainVariableNames.PROC_TYPE, processDefinition.getName());
		
		envVariables.put(ProcessMainVariableNames.PROC_CREATE_TIME, new Date());

		envVariables.put(ProcessMainVariableNames.PROC_CREATE_USERID, userId);
		
		UserInfoModel userInfoModel = userManagementService.getUser(userId);
		envVariables.put(ProcessMainVariableNames.PROC_CREATE_USER, userInfoModel.getUserName());
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,
				envVariables);

		List<Task> nextTaskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();

		String firstTaskId = "";

		if (nextTaskList.size() == 1) {
			firstTaskId = nextTaskList.get(0).getId();
			taskService.setAssignee(firstTaskId, userId);
		}

		return processInstance.getId() + "|" + firstTaskId;
	}

	public void save(String taskId, String comment, Map<String, Object> formData) {

		formData = ensureFormDataNotNull(formData);

		taskService.setVariables(taskId, getEnvVarFromFormData(formData));
		
		List<String> nextUsers = new ArrayList<String>();

		WorkflowActionListener workflowActionListener = getWorkflowActionListenerByTaskId(taskId);

		WorkflowEngineContext workflowEngineContext = new ActivitiWorkflowEngineContext(this);
		workflowEngineContext.setTaskId(taskId);
		workflowEngineContext.setComment(comment);
		workflowEngineContext.setNextUsers(nextUsers);
		workflowEngineContext.setFormData(formData);
		workflowEngineContext.setCurrentTaskDef(getCurrentTaskDef(taskId));
		workflowEngineContext.setNextTaskDef(getNextTaskDef(taskId, getEnvVarFromFormData(formData)));

		if (workflowActionListener != null) {
			workflowActionListener.onSave(workflowEngineContext);
		}

	}

	public void submit(String taskId, List<String> nextUsers, String comment, Map<String, Object> formData) {

		formData = ensureFormDataNotNull(formData);

		WorkflowActionListener workflowActionListener = getWorkflowActionListenerByTaskId(taskId);

		WorkflowEngineContext workflowEngineContext = new ActivitiWorkflowEngineContext(this);
		workflowEngineContext.setTaskId(taskId);
		workflowEngineContext.setComment(comment);
		workflowEngineContext.setNextUsers(nextUsers);
		workflowEngineContext.setFormData(formData);
		workflowEngineContext.setCurrentTaskDef(getCurrentTaskDef(taskId));
		workflowEngineContext.setNextTaskDef(getNextTaskDef(taskId, getEnvVarFromFormData(formData)));
		workflowEngineContext.setChoice(getChoiceFromFormData(formData));
		
		if (workflowActionListener != null) {
			if (workflowEngineContext.getChoice().equals(WorkflowChoiceOptions.SUBMIT)) {
				workflowActionListener.beforSubmit(workflowEngineContext);
			} else if (workflowEngineContext.getChoice().equals(WorkflowChoiceOptions.BACK)) {
				workflowActionListener.beforBack(workflowEngineContext);
			} else if (workflowEngineContext.getChoice().equals(WorkflowChoiceOptions.REJECT)) {
				workflowActionListener.beforReject(workflowEngineContext);
			}
			
		}

		run(taskId, nextUsers, comment, formData);

		if (workflowActionListener != null) {
			if (workflowEngineContext.getChoice().equals(WorkflowChoiceOptions.SUBMIT)) {
				workflowActionListener.afterSubmit(workflowEngineContext);
			} else if (workflowEngineContext.getChoice().equals(WorkflowChoiceOptions.BACK)) {
				workflowActionListener.afterBack(workflowEngineContext);
			} else if (workflowEngineContext.getChoice().equals(WorkflowChoiceOptions.REJECT)) {
				workflowActionListener.afterReject(workflowEngineContext);
			}
		}
		
	}

	public void delete(String taskId, String comment, Map<String, Object> formData) {

		formData = ensureFormDataNotNull(formData);

		WorkflowActionListener workflowActionListener = getWorkflowActionListenerByTaskId(taskId);

		WorkflowEngineContext workflowEngineContext = new ActivitiWorkflowEngineContext(this);
		workflowEngineContext.setTaskId(taskId);
		workflowEngineContext.setComment(comment);
		workflowEngineContext.setFormData(formData);
		workflowEngineContext.setCurrentTaskDef(getCurrentTaskDef(taskId));

		if (workflowActionListener != null) {
			workflowActionListener.beforDelete(workflowEngineContext);
		}

		processInstanceManager.deleteProcessInstanceByTaskId(taskId, comment);

		if (workflowActionListener != null) {
			workflowActionListener.afterDelete(workflowEngineContext);
		}		
		
	}
	
	public void run(String taskId, List<String> nextUsers, String comment, Map<String, Object> formData) {

		formData = ensureFormDataNotNull(formData);

		Map<String, Object> envVariables = getEnvVarFromFormData(formData);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (comment != null) {
			taskService.addComment(taskId, task.getProcessInstanceId(), comment);
		}

		taskService.complete(taskId, envVariables);

		setTaskUser(taskId, nextUsers);

	}

	public NextTaskInfoModel retrieveNextTaskInfo(String taskId, Map<String, Object> formData) {
		
		NextTaskInfoModel result=new NextTaskInfoModel();
		List<UserInfoModel> userInfoModels = new ArrayList<UserInfoModel>();
	
		TaskDefinition taskDefinition = processDefinitionManager.getNextTaskDefinition(taskId,
				getEnvVarFromFormData(formData));
		
		result.setTaskKey(taskDefinition.getKey());
		result.setTaskName(taskDefinition.getNameExpression().toString());
		
		WorkflowEngineContext workflowEngineContext = new ActivitiWorkflowEngineContext(this);
		workflowEngineContext.setTaskId(taskId);
		workflowEngineContext.setFormData(formData);
		workflowEngineContext.setCurrentTaskDef(getCurrentTaskDef(taskId));
		workflowEngineContext.setNextTaskDef(getNextTaskDef(taskId, getEnvVarFromFormData(formData)));

		Expression expression = taskDefinition.getAssigneeExpression();

		if (expression != null) {

			String configValue = taskDefinition.getAssigneeExpression().toString();

			String[] arrConfigValue = configValue.split(":");
			String handlerKey = arrConfigValue[0];
			String handlerParams = arrConfigValue.length > 1 ? arrConfigValue[1] : null;

			if (handlerKey.startsWith("$")) {
				handlerKey = handlerKey.replace("$", "");
				NextTaskUsersHandler nextTaskUsersHandler = nextTaskUsersHandlers.get(handlerKey);

				if (nextTaskUsersHandler == null) {
					throw new HandlerNotFoundException(
							"在“nextTaskUsersHandlers”中找不到key为“" + handlerKey + "”的NextTaskUsersHandler！");
				} else {
					workflowEngineContext.setComment(handlerParams);
					userInfoModels = nextTaskUsersHandler.execute(workflowEngineContext);
				}

			}

		}

		WorkflowActionListener workflowActionListener = getWorkflowActionListenerByTaskId(taskId);
		if (workflowActionListener != null) {
			workflowActionListener.onRetrieveNextTaskUsers(userInfoModels, workflowEngineContext);
		}
		
		result.setUsers(userInfoModels);
		
		return result;
	}

	public Map<String, Object> retrieveFormData(String taskId) {
		
		Map<String, Object> formData=new HashMap<String, Object>();
		
		Map<String, Object> processVariables = processVariablesManager.getProcessVariablesByTaskId(taskId);
		setEnvVarToFormData(processVariables,formData);
		
		TaskDefModel currentTaskDefModel = getCurrentTaskDef(taskId);
	    ProcessDefinition processDefinition=processDefinitionManager.getProcessDefinitionEntityByTaskId(taskId);
		
		formData.put("isFirstTask",processDefinitionManager.isFirstTask(taskId));
		formData.put("currentTaskKey",currentTaskDefModel.getKey());
		formData.put("currentTaskName",currentTaskDefModel.getName());
		formData.put("isRunningTask", userTaskManager.isRunningTask(taskId));
		formData.put("proDefKey", processDefinition.getKey());
		formData.put("proDefName", processDefinition.getName());
		
		
		WorkflowActionListener workflowActionListener = getWorkflowActionListenerByTaskId(taskId);
		if (workflowActionListener != null) {	
			WorkflowEngineContext workflowEngineContext=new ActivitiWorkflowEngineContext(this);
			workflowEngineContext.setTaskId(taskId);
			workflowEngineContext.setFormData(formData);
			workflowEngineContext.setCurrentTaskDef(currentTaskDefModel);
			workflowActionListener.onRetrieveFormData(workflowEngineContext);
		}
		
		return formData;
		
	}
	
	public Map<String, Object> getEnvVarFromFormData(Map<String, Object> formData) {
		Map<String, Object> result = new HashMap<String, Object>();

		for (Entry<String, Object> formItem : formData.entrySet()) {
			if (formItem.getKey().startsWith(ACTIVITI_ENV_VAR_KEY_PREFIX)) {
				result.put(formItem.getKey().replace(ACTIVITI_ENV_VAR_KEY_PREFIX, ""), formItem.getValue());
			}
		}

		return result;
	}

	public void setEnvVarToFormData(Map<String, Object> envVar,Map<String, Object> formData) {
		for (Entry<String, Object> entry : envVar.entrySet()) {
			formData.put(ACTIVITI_ENV_VAR_KEY_PREFIX+entry.getKey(), entry.getValue());
		}
	}

	
	private void setTaskUser(String preTaskId, List<String> users) {
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(preTaskId)
				.singleResult();

		if (users != null && users.size() != 0) {

			List<Task> nextTaskList = taskService.createTaskQuery()
					.processInstanceId(historicTaskInstance.getProcessInstanceId()).list();

			for (Task nextTask : nextTaskList) {
				if (users.size() == 1) {
					// nextTask.setAssignee(nextUsers.get(0));
					taskService.setAssignee(nextTask.getId(), users.get(0));
				} else {

					for (String userId : users) {
						taskService.addCandidateUser(nextTask.getId(), userId);
					}
				}
			}

		}
	}

	private String getChoiceFromFormData(Map<String, Object> formData) {

		return formData.get(ACTIVITI_ENV_VAR_KEY_PREFIX + WorkflowChoiceOptions.getStoreKey()).toString();
	}

	private Map<String, Object> ensureFormDataNotNull(Map<String, Object> formData) {
		if (formData == null) {
			formData = new HashMap<String, Object>();
		}
		return formData;
	}

	private WorkflowActionListener getWorkflowActionListenerByTaskId(String taskId) {

		ProcessDefinitionEntity processDefinitionEntity = processDefinitionManager
				.getProcessDefinitionEntityByTaskId(taskId);

		WorkflowActionListener workflowActionListener = workflowActionListeners.get(processDefinitionEntity.getKey());

		return workflowActionListener;
	}

	private TaskDefModel getCurrentTaskDef(String taskId) {
		TaskDefModel taskDefModel = new TaskDefModel();

		TaskDefinition taskDefinition = processDefinitionManager.getTaskDefinition(taskId);

		taskDefModel.setKey(taskDefinition.getKey());
		taskDefModel.setName(taskDefinition.getNameExpression().toString());

		return taskDefModel;
	}

	private TaskDefModel getNextTaskDef(String taskId, Map<String, Object> params) {
		TaskDefModel taskDefModel = new TaskDefModel();

		TaskDefinition taskDefinition = processDefinitionManager.getNextTaskDefinition(taskId, params);

		if (taskDefinition != null) {

			taskDefModel.setKey(taskDefinition.getKey());
			taskDefModel.setName(taskDefinition.getNameExpression().toString());
		} else {
			taskDefModel = null;
		}

		return taskDefModel;
	}



}
