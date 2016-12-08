package com.cs.test.exception;

public class SCRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public SCRuntimeException(String msg) {
		super(msg);
	}

	public SCRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
