package com.cs.test.exception;

public class QueueRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public QueueRuntimeException(String msg) {
		super(msg);
	}

	public QueueRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
