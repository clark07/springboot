package com.cs.test.exception;

public class ConvertRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public ConvertRuntimeException(String msg) {
		super(msg);
	}

	public ConvertRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
