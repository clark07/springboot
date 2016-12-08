package com.cs.test.exception;

public class InvalidParamException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public InvalidParamException(String msg) {
		super(msg);
	}

	public InvalidParamException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
