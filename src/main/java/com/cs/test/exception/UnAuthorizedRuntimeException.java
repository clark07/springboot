package com.cs.test.exception;

public class UnAuthorizedRuntimeException extends SCRuntimeException {

	private static final long serialVersionUID = 7229833627236807221L;

	public UnAuthorizedRuntimeException(String msg) {
		super(msg);
	}

	public UnAuthorizedRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
