package com.cs.test.response;

/**
 * Created by admin on 2016/12/26.
 */
public enum BootReturnCode {

	SUCC(0),
	ERROR(-1);

	private int value;

	private BootReturnCode(int value){this.value = value;}

	public int getValue(){
		return value;
	}


	public String getDefaultMsg(){
		if (value == SUCC.getValue())
			return "成功";
		else if (value == ERROR.getValue())
			return "错误";
		else
			return "";
	}

}
