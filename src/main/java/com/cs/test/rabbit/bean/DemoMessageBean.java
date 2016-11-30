package com.cs.test.rabbit.bean;

import lombok.Data;

/**
 * Created by admin on 2016/11/30.
 */
@Data
public class DemoMessageBean extends MessageBean {
	private Object obj;

	public DemoMessageBean(Object obj) {
		this.obj = obj;
	}

	public DemoMessageBean() {
	}
}
