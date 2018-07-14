package com.zhuang.workflow.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ActivitiJUELUtilsTest {

    @Test
    public void evaluateBooleanResult() {
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("count", 1000);
        System.out.println(ActivitiJUELUtils.evaluateBooleanResult("${count>1000 || 1==1}", map));
    }
}