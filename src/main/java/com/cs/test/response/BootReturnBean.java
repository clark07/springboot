package com.cs.test.response;

import lombok.Data;

/**
 * Created by admin on 2016/12/16.
 */
@Data
public class BootReturnBean {
	private int code;
	private String msg;
	private Object data;

	public BootReturnBean() {
	}

	public BootReturnBean(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
