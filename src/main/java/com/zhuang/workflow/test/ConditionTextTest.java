package com.zhuang.workflow.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.zhuang.workflow.util.ActivitiJUELUtils;

public class ConditionTextTest {

	@Test
	public void testJUEL() {

		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("count", 1000);
		
		System.out.println(ActivitiJUELUtils.evaluateBooleanResult("${count>1000 || 1==1}", map));
		
	}
	
}
