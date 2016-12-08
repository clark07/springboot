package com.cs.test.exception;

public class InvalidRequestRuntimeException extends SCRuntimeException {

	private static final long serialVersionUID = 3568395377136183710L;

	public InvalidRequestRuntimeException(String msg) {
		super(msg);
	}

	public InvalidRequestRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
