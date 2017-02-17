package com.zhuang.workflow.activiti;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.aspectj.apache.bcel.generic.AALOAD;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.exceptions.HistoricTaskNotFoundException;
import com.zhuang.workflow.exceptions.RunningTaskNotFoundException;
import com.zhuang.workflow.util.ActivitiJUELUtil;

public class ProcessDefinitionManager {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	public ProcessDefinitionEntity getProcessDefinitionEntityByTaskId(String taskId) {

		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
				.singleResult();

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) (((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(historicTaskInstance.getProcessDefinitionId()));

		return def;
	}

	public ProcessDefinitionEntity getProcessDefinitionEntityByKey(String proDefkey) {

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(proDefkey).latestVersion().singleResult();
		return def;
	}

	public ActivityImpl getActivityImpl(String taskId) {

		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
				.singleResult();

		if (historicTaskInstance == null) {
			throw new HistoricTaskNotFoundException("taskId:" + taskId);
		}

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) (((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(historicTaskInstance.getProcessDefinitionId()));

		String activitiId = historicTaskInstance.getTaskDefinitionKey();

		List<ActivityImpl> activitiList = def.getActivities();

		for (ActivityImpl activityImpl : activitiList) {

			if (activitiId.equals(activityImpl.getId())) {// 当前任务
				return activityImpl;
			}
		}

		return null;
	}

	public TaskDefinition getTaskDefinition(String taskId) {

		ActivityImpl activityImpl = getActivityImpl(taskId);

		if (activityImpl != null) {
			return ((UserTaskActivityBehavior) activityImpl.getActivityBehavior()).getTaskDefinition();
		}

		return null;
	}

	public TaskDefinition getNextTaskDefinition(String taskId, Map<String, Object> params) {

		ActivityImpl activityImpl = getActivityImpl(taskId);

		if (activityImpl != null) {
			return getNextTaskDefinition(activityImpl, params);
		}

		return null;
	}

	public boolean isFirstTask(String taskId) {

		boolean result = false;

		ActivityImpl activityImpl = getActivityImpl(taskId);

		List<PvmTransition> incomingTransitions = activityImpl.getIncomingTransitions();

		for (PvmTransition pvmTransition : incomingTransitions) {

			PvmActivity pvmActivity = pvmTransition.getSource();

			if (pvmActivity.getProperty("type").equals("startEvent")) {
				result = true;
				break;
			}
		}

		return result;
	}

	private TaskDefinition getNextTaskDefinition(ActivityImpl activityImpl, Map<String, Object> params) {

		List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();

		if (outgoingTransitions.size() == 1) {

			PvmActivity outActi = outgoingTransitions.get(0).getDestination();

			if ("exclusiveGateway".equals(outActi.getProperty("type"))) {

				List<PvmTransition> gatewayOutgoingTransitions = outActi.getOutgoingTransitions();

				TaskDefinition taskDefinition = getNextTaskDefinition(gatewayOutgoingTransitions, params);

				if (taskDefinition != null) {
					return taskDefinition;
				}

			} else if ("userTask".equals(outActi.getProperty("type"))) {

				return ((UserTaskActivityBehavior) ((ActivityImpl) outActi).getActivityBehavior()).getTaskDefinition();

			} else {

				System.out.println(outActi.getProperty("type"));

			}

		} else if(outgoingTransitions.size()>1){

			TaskDefinition taskDefinition = getNextTaskDefinition(outgoingTransitions, params);

			if (taskDefinition != null) {
				return taskDefinition;
			}

		}
		
		return null;

	}

	private TaskDefinition getNextTaskDefinition(List<PvmTransition> outgoingTransitions,
			Map<String, Object> params) {

		TaskDefinition result = null;

		if (outgoingTransitions.size() == 1) {

			ActivityImpl tempActivityImpl = (ActivityImpl) outgoingTransitions.get(0).getDestination();

			if ("userTask".equals(tempActivityImpl.getProperty("type"))) {
				return ((UserTaskActivityBehavior) tempActivityImpl.getActivityBehavior()).getTaskDefinition();

			} else {
				return getNextTaskDefinition(tempActivityImpl, params);
			}

		} else if (outgoingTransitions.size() > 1) {

			PvmTransition correctGwOutTransi = null;
			PvmTransition defaultGwOutTransi = null;

			for (PvmTransition gwOutTransi : outgoingTransitions) {

				Object conditionText = gwOutTransi.getProperty("conditionText");

				if (conditionText == null) {
					defaultGwOutTransi = gwOutTransi;
				}

				if (conditionText != null && ActivitiJUELUtil.evaluateBooleanResult(conditionText.toString(), params)) {

					correctGwOutTransi = gwOutTransi;
				}
			}

			if (correctGwOutTransi == null) {
				correctGwOutTransi = defaultGwOutTransi;
			}

			if (correctGwOutTransi != null) {
				ActivityImpl tempActivityImpl = (ActivityImpl) correctGwOutTransi.getDestination();
				if ("userTask".equals(tempActivityImpl.getProperty("type"))) {
					result = ((UserTaskActivityBehavior) tempActivityImpl.getActivityBehavior()).getTaskDefinition();

				} else {
					result = getNextTaskDefinition(tempActivityImpl, params);
				}
			}

		}
		return result;
	}

	public List<ProcessDefinition> getProcessDefinitionList() {
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().active()
				.latestVersion().list();
		return processDefinitions;
	}
}
