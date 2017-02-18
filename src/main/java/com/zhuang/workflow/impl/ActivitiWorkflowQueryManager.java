package com.zhuang.workflow.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.activiti.engine.task.TaskQuery;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.WorkflowQueryManager;
import com.zhuang.workflow.activiti.ProcessDefinitionManager;
import com.zhuang.workflow.activiti.ProcessInstanceManager;
import com.zhuang.workflow.activiti.ProcessMainVariableNames;
import com.zhuang.workflow.activiti.ProcessVariablesManager;
import com.zhuang.workflow.commons.PageModel;
import com.zhuang.workflow.models.EndTaskVariableNames;
import com.zhuang.workflow.models.FlowInfoModel;
import com.zhuang.workflow.models.ProcDefModel;
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
	private ProcessInstanceManager processInstanceManager;
	
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
			
			
			String currentActivityName="";
			if(processInstanceManager.isProcessFinished(historicTaskInstance.getProcessInstanceId()))
			{
				currentActivityName= EndTaskVariableNames.NAME;
			}else
			{

			    List<Task> tasks =taskService.createTaskQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).list();
				if(tasks.size()>0)
				{
					currentActivityName=tasks.get(0).getName();
				}
			}
			
			flowInfoModel.setCurrentActivityName(currentActivityName);

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
		
		if(taskInfoModels.size()>0)
		{
			TaskInfoModel lastTask = taskInfoModels.get(taskInfoModels.size() - 1);
			if (lastTask.getEndTime() != null) {
				boolean isEndTask = processDefinitionManager.isEndTask(lastTask.getId());
				if (isEndTask) {
					TaskInfoModel taskInfoModel = new TaskInfoModel();

					taskInfoModel.setId(EndTaskVariableNames.ID);
					taskInfoModel.setKey(EndTaskVariableNames.KEY);
					taskInfoModel.setName(EndTaskVariableNames.NAME);
					taskInfoModel.setUserId(EndTaskVariableNames.USERID);
					taskInfoModel.setUserName(EndTaskVariableNames.USERNAME);
					taskInfoModel.setStartTime(lastTask.getEndTime());
					taskInfoModel.setEndTime(lastTask.getEndTime());
					taskInfoModel.setComment(EndTaskVariableNames.COMMENT);

					taskInfoModels.add(taskInfoModel);

				}
			}
		}

		return taskInfoModels;
	}
	
	private void setTaskQueryConditions(TaskInfoQuery taskInfoQuery, Map<String, Object> conditions) {
		
		if (conditions != null) {

			if (conditions.containsKey(ProcessMainVariableNames.PROC_DEF_KEY)) {
				Object objProcDefKey = conditions.get(ProcessMainVariableNames.PROC_DEF_KEY);
				if (objProcDefKey != null && objProcDefKey.toString().trim() != "") {
					taskInfoQuery.processVariableValueEquals(ProcessMainVariableNames.PROC_DEF_KEY,
							objProcDefKey);
				}
			}
			
			if (conditions.containsKey(ProcessMainVariableNames.PROC_TYPE)) {
				Object objProcType = conditions.get(ProcessMainVariableNames.PROC_TYPE);
				if (objProcType != null && objProcType.toString().trim() != "") {
					taskInfoQuery.processVariableValueEquals(ProcessMainVariableNames.PROC_TYPE,
							objProcType);
				}
			}

			if (conditions.containsKey(ProcessMainVariableNames.PROC_TITLE)) {
				Object objProcTitle = conditions.get(ProcessMainVariableNames.PROC_TITLE);
				if (objProcTitle != null && objProcTitle.toString().trim() != "") {
					taskInfoQuery.processVariableValueLike(ProcessMainVariableNames.PROC_TITLE,
							"%" +objProcTitle.toString() +"%");
				}
			}
			
			if (conditions.containsKey(ProcessMainVariableNames.PROC_CREATE_USER)) {
				Object objProcCreateUserName = conditions.get(ProcessMainVariableNames.PROC_CREATE_USER);
				if (objProcCreateUserName != null && objProcCreateUserName.toString().trim() != "") {
					taskInfoQuery.processVariableValueLike(ProcessMainVariableNames.PROC_CREATE_USER,
							"%" +objProcCreateUserName.toString() +"%");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String proCreateTimeStart=ProcessMainVariableNames.PROC_CREATE_TIME+"_START";
			if (conditions.containsKey(proCreateTimeStart)) {
				Object objProcCreateTimeStart = conditions.get(proCreateTimeStart);
				if (objProcCreateTimeStart != null && objProcCreateTimeStart.toString().trim()!="") {
					Date dProcCreateTimeStart=null;
					try {
						dProcCreateTimeStart = sdf.parse(objProcCreateTimeStart.toString()+" 00:00:00");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					taskInfoQuery.processVariableValueGreaterThanOrEqual(ProcessMainVariableNames.PROC_CREATE_TIME,dProcCreateTimeStart);
				}
			}
			
			String proCreateTimeEnd=ProcessMainVariableNames.PROC_CREATE_TIME+"_END";
			if (conditions.containsKey(proCreateTimeEnd)) {
				Object objProcCreateTimeEnd = conditions.get(proCreateTimeEnd);
				if (objProcCreateTimeEnd != null && objProcCreateTimeEnd.toString().trim()!="") {
					Date dProcCreateTimeEnd=null;
					try {
						dProcCreateTimeEnd = sdf.parse(objProcCreateTimeEnd.toString()+" 23:59:59");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					taskInfoQuery.processVariableValueLessThanOrEqual(ProcessMainVariableNames.PROC_CREATE_TIME,dProcCreateTimeEnd);
				}
			}

		}

	}

	private void fillFlowInfoModel(FlowInfoModel flowInfoModel, Map<String, Object> processVariables) {
		
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_DEF_KEY)) {
			flowInfoModel.setDefKey(processVariables.get(ProcessMainVariableNames.PROC_DEF_KEY).toString());
		}
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_TITLE)) {
			flowInfoModel.setTitle(processVariables.get(ProcessMainVariableNames.PROC_TITLE).toString());
		}
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_TYPE)) {
			flowInfoModel.setType(processVariables.get(ProcessMainVariableNames.PROC_TYPE).toString());
		}
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_CREATE_TIME)) {
			flowInfoModel.setCreateTime((Date)processVariables.get(ProcessMainVariableNames.PROC_CREATE_TIME));
		}
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_CREATE_USERID)) {
			flowInfoModel.setCreateUserId(processVariables.get(ProcessMainVariableNames.PROC_CREATE_USERID).toString());
		}
		if (processVariables.containsKey(ProcessMainVariableNames.PROC_CREATE_USER)) {
			flowInfoModel.setCreateUser(processVariables.get(ProcessMainVariableNames.PROC_CREATE_USER).toString());
		}

	}

	public List<ProcDefModel> getProcDefList() {

		List<ProcDefModel> procDefModels=new ArrayList<ProcDefModel>();
		
		List<ProcessDefinition> processDefinitions = processDefinitionManager.getProcessDefinitionList();
	
		for (ProcessDefinition processDefinition : processDefinitions) {
			ProcDefModel procDefModel=new ProcDefModel();
			procDefModel.setKey(processDefinition.getKey());
			procDefModel.setName(processDefinition.getName());
			procDefModels.add(procDefModel);
		}
		
		return procDefModels;
	}

}
