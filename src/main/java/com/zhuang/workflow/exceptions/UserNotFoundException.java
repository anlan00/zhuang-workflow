package com.zhuang.workflow.exceptions;

/**
 * 没有找到用户导常
 * @author zwb
 *
 */
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String s) {
		super(s);
	}

}
