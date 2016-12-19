package com.cs.test.controller;

import lombok.Data;

/**
 * Created by admin on 2016/12/16.
 */
@Data
public class BootReturnCode {
	private int code;
	private String msg;
	private Object data;

	public BootReturnCode() {
	}

	public BootReturnCode(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
