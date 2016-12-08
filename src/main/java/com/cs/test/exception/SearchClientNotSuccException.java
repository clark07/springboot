package com.cs.test.exception;

public class SearchClientNotSuccException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	public SearchClientNotSuccException(String msg) {
		super(msg);
	}

	public SearchClientNotSuccException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
