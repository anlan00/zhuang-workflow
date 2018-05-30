package com.zhuang.workflow;

import java.io.InputStream;
import java.util.Map;

import com.zhuang.workflow.common.PageModel;
import com.zhuang.workflow.model.DeploymentInfoModel;

public interface WorkflowDeployment {
	
	void deployByInputStream(String resourceName, InputStream inputStream);

	PageModel<DeploymentInfoModel> getDeploymentInfoPage( int pageNo, int pageSize, Map<String, Object> conditions);
}
