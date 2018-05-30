package com.zhuang.workflow.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuang.workflow.WorkflowDeployment;
import com.zhuang.workflow.activiti.DeploymentManager;
import com.zhuang.workflow.common.PageModel;
import com.zhuang.workflow.enums.DeploymentInfoNames;
import com.zhuang.workflow.model.DeploymentInfoModel;

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
		
		
		
		setDeploymentQueryConditions(deploymentQuery,conditions);
	
		
		//得到分页记录
		List<Deployment> deployments = deploymentQuery.listPage(result.getPageStartRow() - 1, result.getPageSize());

		for (Deployment deployment : deployments) {
			
			DeploymentInfoModel deploymentInfoModel=new DeploymentInfoModel();
			
			deploymentInfoModel.setDeployId(deployment.getId());
			deploymentInfoModel.setDeployName(deployment.getName());
			deploymentInfoModel.setDeployCategory(deployment.getCategory());
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


	
	private void setDeploymentQueryConditions(DeploymentQuery deploymentQuery, Map<String, Object> conditions) {
		
		if (conditions != null) {
			
			if (conditions.containsKey(DeploymentInfoNames.PROC_DEF_KEY)) {
				Object objProcDefKey = conditions.get(DeploymentInfoNames.PROC_DEF_KEY);
				if (objProcDefKey != null && objProcDefKey.toString().trim() != "") {
					deploymentQuery.processDefinitionKey(objProcDefKey.toString().trim());
				}
			}

			if (conditions.containsKey(DeploymentInfoNames.DEPLOY_NAME)) {
				Object objDeployName = conditions.get(DeploymentInfoNames.DEPLOY_NAME);
				if (objDeployName != null && objDeployName.toString().trim() != "") {
					deploymentQuery.deploymentNameLike("%" +objDeployName.toString() +"%");
				}
			}

		}

	}
	
}
