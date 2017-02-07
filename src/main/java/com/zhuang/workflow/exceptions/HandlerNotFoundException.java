package com.zhuang.workflow.exceptions;

/**
 * 没有找到处理类异常
 * @author zwb
 *
 */
public class HandlerNotFoundException extends RuntimeException {

	public HandlerNotFoundException() {
		super();
	}

	public HandlerNotFoundException(String s) {
		super(s);
	}

}
