package com.cs.fastjson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by admin on 2017/1/11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericTypeJsonBean<T, U> {
	private T t;
	private U u;
	private String value;
}
