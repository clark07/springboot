package com.cs.fastjson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by admin on 2017/1/11.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComplexJsonBean {
	private String value;
	private JsonBean jsonBean;
}
