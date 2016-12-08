package com.cs.test.exception;

public class TargetNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public TargetNotFoundException(String msg) {
		super(msg);
	}

	public TargetNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
