package com.cs.test.rabbit;

import lombok.Data;

/**
 * Created by admin on 2016/11/18.
 */
@Data
public class RabbitTestBean {
	private String content;

	public RabbitTestBean() {
	}

	public RabbitTestBean(String content) {
		this.content = content;
	}
}
