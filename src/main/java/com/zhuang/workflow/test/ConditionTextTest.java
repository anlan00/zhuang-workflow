package com.zhuang.workflow.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.junit.Test;

import com.zhuang.workflow.utils.ActivitiJUELUtil;

public class ConditionTextTest {

	@Test
	public void testJUEL() {

		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("count", 1000);
		
		System.out.println(ActivitiJUELUtil.evaluateBooleanResult("${count>1000 || 1==1}", map));
		
	}
	
}
