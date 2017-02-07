package com.zhuang.workflow.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	public static String getStackTrace(Exception e) {
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			e.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
		
	}
}
