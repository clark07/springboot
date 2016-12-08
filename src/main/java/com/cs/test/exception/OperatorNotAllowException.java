package com.cs.test.exception;

public class OperatorNotAllowException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public OperatorNotAllowException(String msg) {
		super(msg);
	}

	public OperatorNotAllowException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
