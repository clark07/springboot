package com.cs.test.exception;

public class HttpClientException extends RuntimeException {

	private static final long serialVersionUID = -1413861554881895281L;

	public HttpClientException(String msg) {
		super(msg);
	}

	public HttpClientException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
