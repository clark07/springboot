package com.cs.test.exception;

public class ResourceNotFoundRuntimeException extends SCRuntimeException {
	private static final long serialVersionUID = 2994946122557149790L;

	public ResourceNotFoundRuntimeException(String msg) {
		super(msg);
	}

	public ResourceNotFoundRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
