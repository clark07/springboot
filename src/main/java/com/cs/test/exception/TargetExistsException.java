package com.cs.test.exception;

public class TargetExistsException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public TargetExistsException(String msg) {
		super(msg);
	}

	public TargetExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
