package com.cs.test.exception;

public class RedisRuntimeException extends SCRuntimeException {

	private static final long serialVersionUID = -8381946107398995340L;

	public RedisRuntimeException(String msg) {
		super(msg);
	}

	public RedisRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
