package com.zhuang.workflow.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.WorkflowQueryManager;
import com.zhuang.workflow.activiti.ProcessDefinitionManager;
import com.zhuang.workflow.activiti.ProcessMainVariableNames;
import com.zhuang.workflow.activiti.ProcessVariablesManager;
import com.zhuang.workflow.commons.PageModel;
import com.zhuang.workflow.models.FlowInfoModel;
import com.zhuang.workflow.models.TaskInfoModel;
import com.zhuang.workflow.services.UserManagementService;

public class ActivitiWorkflowQueryManager implements WorkflowQueryManager {

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private UserManagementService userManagementService;
	
	@Autowired
	private ProcessVariablesManager processVariablesManager;

	@Autowired
	ProcessDefinitionManager processDefinitionManager;
	
	public PageModel<FlowInfoModel> getMyTodoListPage(String userId, int pageNo, int pageSize,

			Map<String, Object> conditions) {

		//总记录查询
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);

		List<FlowInfoModel> flowInfoList = new ArrayList<FlowInfoModel>();

		//设置查询筛选条件
		setTaskQueryConditions(taskQuery, conditions);
		
		//设置排序
		taskQuery.orderByTaskCreateTime().desc();
		
		PageModel<FlowInfoModel> result = new PageModel<FlowInfoModel>(pageNo, pageSize, new Long(taskQuery.count()).intValue(),
				flowInfoList);
		
		//得到分页记录
		List<Task> taskList = taskQuery.listPage(result.getPageStartRow() - 1, result.getPageSize());

		//设置流程信息实体值
		for (Task task : taskList) {

			FlowInfoModel flowInfoModel = new FlowInfoModel();
			flowInfoModel.setTaskId(task.getId());
			flowInfoModel.setCurrentActivityName(task.getName());

			HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
					.createHistoricProcessInstanceQuery();
			HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery
					.processInstanceId(task.getProcessInstanceId()).singleResult();
			flowInfoModel.setApplyUserId(historicProcessInstance.getStartUserId());
			flowInfoModel.setApplyUser(userManagementService.getUser(flowInfoModel.getApplyUserId()).getUserName());
			flowInfoModel.setApplyTime(historicProcessInstance.getStartTime());
			flowInfoModel.setDefKey(historicProcessInstance.getProcessDefinitionId());
			
			Map<String, Object> processVariables = runtimeService.getVariables(task.getExecutionId());
			fillFlowInfoModel(flowInfoModel, processVariables);
			flowInfoList.add(flowInfoModel);
		}

		return result;
	}

	public PageModel<FlowInfoModel> getMyDoneListPage(String userId, int pageNo, int pageSize,
			Map<String, Object> conditions) {

		//总记录查询
		HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
				.taskAssignee(userId).finished();
		
		List<FlowInfoModel> flowInfoList = new ArrayList<FlowInfoModel>();

		//设置查询筛选条件
		setTaskQueryConditions(historicTaskInstanceQuery, conditions);

		//设置排序
		historicTaskInstanceQuery.orderByTaskCreateTime().desc();
		
		PageModel<FlowInfoModel> result = new PageModel<FlowInfoModel>(pageNo, pageSize, new Long(historicTaskInstanceQuery.count()).intValue(),
				flowInfoList);

		//得到分页记录
		List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.listPage(result.getPageStartRow() - 1, result.getPageSize());

		//设置流程信息实体值
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {

			FlowInfoModel flowInfoModel = new FlowInfoModel();
			flowInfoModel.setTaskId(historicTaskInstance.getId());
			flowInfoModel.setCurrentActivityName(historicTaskInstance.getName());

			HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
					.createHistoricProcessInstanceQuery();
			HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery
					.processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
			flowInfoModel.setApplyUserId(historicProcessInstance.getStartUserId());
			flowInfoModel.setApplyUser(userManagementService.getUser(flowInfoModel.getApplyUserId()).getUserName());
			flowInfoModel.setApplyTime(historicProcessInstance.getStartTime());
			flowInfoModel.setDefKey(historicProcessInstance.getProcessDefinitionId());
			
			Map<String, Object> processVariables = processVariablesManager.getProcessVariablesByTaskId(historicTaskInstance.getId());
			fillFlowInfoModel(flowInfoModel, processVariables);
			flowInfoList.add(flowInfoModel);
		}

		return result;

	}

	public List<TaskInfoModel> getHistoryTaskInfoList(String taskId) {
		
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		
		String instanceId=historicTaskInstance.getProcessInstanceId();
		
		List<TaskInfoModel> taskInfoModels=getHistoryTaskInfoListByInstanceId(instanceId);

		return taskInfoModels;
	}

	private List<TaskInfoModel> getHistoryTaskInfoListByInstanceId(String instanceId) {
		List<TaskInfoModel> taskInfoModels= new ArrayList<TaskInfoModel>();
		
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(instanceId).orderByTaskCreateTime().asc().list();
		
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
		
			TaskInfoModel taskInfoModel=new TaskInfoModel();
			
			taskInfoModel.setId(historicTaskInstance.getId());
			taskInfoModel.setKey(historicTaskInstance.getTaskDefinitionKey());
			taskInfoModel.setName(historicTaskInstance.getName());
			taskInfoModel.setUserId(historicTaskInstance.getAssignee());
			taskInfoModel.setUserName(userManagementService.getUser(taskInfoModel.getUserId()).getUserName());
			taskInfoModel.setStartTime(historicTaskInstance.getStartTime());
			taskInfoModel.setEndTime(historicTaskInstance.getEndTime());
			List<Comment> comments = taskService.getTaskComments(historicTaskInstance.getId());
			if(comments.size()>0)
			{
				taskInfoModel.setComment(comments.get(0).getFullMessage());
			}
			taskInfoModels.add(taskInfoModel);
		}

		return taskInfoModels;
	}
	
	private void setTaskQueryConditions(TaskInfoQuery taskInfoQuery, Map<String, Object> conditions) {
		
		if (conditions != null) {

			if (conditions.containsKey(ProcessMainVariableNames.PROC_TYPE)) {
				Object objProcType = conditions.get(ProcessMainVariableNames.PROC_TYPE);
				if (objProcType != null && objProcType.toString().trim() != "") {
					taskInfoQuery.processVariableValueEquals(ProcessMainVariableNames.PROC_TYPE,
							conditions.get(ProcessMainVariableNames.PROC_TYPE));
				}
			}

			if (conditions.containsKey(ProcessMainVariableNames.PROC_TITLE)) {
				Object objProcTitle = conditions.get(ProcessMainVariableNames.PROC_TITLE);
				if (objProcTitle != null && objProcTitle.toString().trim() != "") {
					taskInfoQuery.processVariableValueLike(ProcessMainVariableNames.PROC_TITLE,
							"%" +conditions.get(ProcessMainVariableNames.PROC_TITLE).toString() +"%");
				}
			}

		}

	}

	private void fillFlowInfoModel(FlowInfoModel flowInfoModel, Map<String, Object> processVariables) {
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_TITLE)) {
			flowInfoModel.setTitle(processVariables.get(ProcessMainVariableNames.PROC_TITLE).toString());
		}
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_TYPE)) {
			flowInfoModel.setType(processVariables.get(ProcessMainVariableNames.PROC_TYPE).toString());
		}
	}

}
