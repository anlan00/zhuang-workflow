package com.zhuang.workflow.exceptions;

/**
 * 没有找到运行中的任务
 * @author zwb
 *
 */
public class RunningTaskNotFoundException extends RuntimeException {

	public RunningTaskNotFoundException() {
		super();
	}

	public RunningTaskNotFoundException(String s) {
		super(s);
	}

}
