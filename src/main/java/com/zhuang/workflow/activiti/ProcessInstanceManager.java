package com.zhuang.workflow.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessInstanceManager {

	@Autowired
	HistoryService historyService;

	public String getApplyUserId(String taskId) {
		
		HistoricTaskInstance historicTaskInstance =historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
				.createHistoricProcessInstanceQuery();
		HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery
				.processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
		
		return historicProcessInstance.getStartUserId();
		
	}
	
}
