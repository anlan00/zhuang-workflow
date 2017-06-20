package com.zhuang.workflow;

import java.text.ParseException;
import org.springframework.context.ApplicationContext;

import com.zhuang.workflow.impl.ActivitiWorkflowEngine;
import com.zhuang.workflow.utils.ApplicationContextUtil;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws ParseException {

		ApplicationContext applicationContext = ApplicationContextUtil.GetApplicationContext();
		ActivitiWorkflowEngine activitiWorkflowEngine = applicationContext.getBean("workflowEngine",
				ActivitiWorkflowEngine.class);

		System.out.println("end");

	}
}
