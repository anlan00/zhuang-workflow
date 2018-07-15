package com.zhuang.workflow.activiti;

import com.zhuang.workflow.WorkflowBeansFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeploymentManagerTest {

    @Test
    public void getBasePath() {
    }

    @Test
    public void setBasePath() {
    }

    @Test
    public void deployByClasspathResource() {
        WorkflowBeansFactory.getDeploymentManager().deployByClasspathResource("test01.bpmn", "test01");
        System.out.println("success!");
    }

    @Test
    public void deployByInputStream() {
    }
}