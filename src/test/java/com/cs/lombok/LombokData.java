package com.cs.lombok;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Created by admin on 2016/12/26.
 */
@Data
//@Value
@Builder
@ToString(of = "name")
public class LombokData {
	private int id;
	private String name;
}
