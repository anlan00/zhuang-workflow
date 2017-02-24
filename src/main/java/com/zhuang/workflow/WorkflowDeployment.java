package com.zhuang.workflow;

import java.io.InputStream;
import java.util.Map;

import com.zhuang.workflow.commons.PageModel;
import com.zhuang.workflow.models.DeploymentInfoModel;

public interface WorkflowDeployment {
	
	void deployByInputStream(String resourceName, InputStream inputStream);

	PageModel<DeploymentInfoModel> getDeploymentInfoPage( int pageNo, int pageSize, Map<String, Object> conditions);
}
