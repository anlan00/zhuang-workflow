package com.zhuang.workflow;

import java.io.InputStream;

public interface WorkflowDeployment {
	
	void deployByInputStream(String resourceName, InputStream inputStream);

}
