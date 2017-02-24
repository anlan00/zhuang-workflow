package com.zhuang.workflow.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.WorkflowDeployment;
import com.zhuang.workflow.activiti.DeploymentManager;
import com.zhuang.workflow.commons.PageModel;
import com.zhuang.workflow.models.DeploymentInfoModel;
import com.zhuang.workflow.models.FlowInfoModel;

public class ActivitiWorkflowDeployment implements WorkflowDeployment{

	@Autowired
	private DeploymentManager deploymentManager;
	
	@Autowired
	private RepositoryService repositoryService;
	
	public void deployByInputStream(String resourceName, InputStream inputStream) {
		deploymentManager.deployByInputStream(resourceName, inputStream);
	}

	public PageModel<DeploymentInfoModel> getDeploymentInfoPage(int pageNo, int pageSize,
			Map<String, Object> conditions) {

		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
		deploymentQuery.orderByDeploymenTime().desc();
		
		
		List<DeploymentInfoModel> deploymentInfoList = new ArrayList<DeploymentInfoModel>();
		
		PageModel<DeploymentInfoModel> result = new PageModel<DeploymentInfoModel>(pageNo, pageSize, new Long(deploymentQuery.count()).intValue(),
				deploymentInfoList);
		
		
		//得到分页记录
		List<Deployment> deployments = deploymentQuery.listPage(result.getPageStartRow() - 1, result.getPageSize());

		for (Deployment deployment : deployments) {
			
			DeploymentInfoModel deploymentInfoModel=new DeploymentInfoModel();
			
			deploymentInfoModel.setId(deployment.getId());
			deploymentInfoModel.setName(deployment.getName());
			deploymentInfoModel.setCategory(deployment.getCategory());
			deploymentInfoModel.setDeployTime(deployment.getDeploymentTime());
			
			
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
			deploymentInfoModel.setProcDefKey(processDefinition.getKey());
			deploymentInfoModel.setProcDefName(processDefinition.getName());
			deploymentInfoModel.setProcDefVersion(processDefinition.getVersion());
			deploymentInfoModel.setProcDefDescription(processDefinition.getDescription());
			
			deploymentInfoList.add(deploymentInfoModel);
		}
		
		return result;
	}


	

	
}
