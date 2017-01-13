package com.cs.fastjson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by admin on 2017/1/11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "name"})
public class JsonBean {
	private int id;
	private String name;
}
