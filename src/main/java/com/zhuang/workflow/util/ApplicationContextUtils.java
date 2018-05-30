package com.zhuang.workflow.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextUtils {

	private static ApplicationContext applicationContext;
	
	static{
		
		if (applicationContext == null) {
			
			applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/applicationContext.xml"});
		}
	}
	
	public static ApplicationContext GetApplicationContext() {
	
		return applicationContext;
		
	}
	
}
