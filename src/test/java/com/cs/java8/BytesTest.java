package com.cs.java8;

import java.util.LinkedHashMap;
import java.util.Map;

public class BytesTest {

	// 网贷
	private int status1;
	// 不良
	private int status2;
	// 失信被执行人
	private int status3;
	// 被执行人
	private int status4;

	public int getStatus1() {
		return status1;
	}

	public void setStatus1(int status1) {
		this.status1 = status1;
	}

	public int getStatus2() {
		return status2;
	}

	public void setStatus2(int status2) {
		this.status2 = status2;
	}

	public int getStatus3() {
		return status3;
	}

	public void setStatus3(int status3) {
		this.status3 = status3;
	}

	public int getStatus4() {
		return status4;
	}

	public void setStatus4(int status4) {
		this.status4 = status4;
	}

	public static void main(String[] args) {
		yourMethod();

		csMethod();

	}

	private static void csMethod() {
		BytesTest bytes = new BytesTest();
		bytes.setStatus1(1);
		bytes.setStatus2(1);
		bytes.setStatus3(1);
		// 状态4位 有数据为1 无数据为0 ，顺序：被执行人 失信被执行人 不良 网贷

		System.out.println("状态码：" + String.format("%s%s%s%s", bytes.getStatus4(),bytes.getStatus3(), bytes.getStatus2(), bytes.getStatus1()));

		System.out.println("描述： " + String.format("%s %s %s %s",  Status4.getMessage(bytes.getStatus4()), Status3
				.getMessage(bytes.getStatus3()), Status2.getMessage(bytes.getStatus2()), Status1.getMessage(bytes.getStatus1())).replaceAll("\\s+", " "));
	}

	static enum Status1{
		FALSE(0, "非网贷"),
		TRUE(1, "网贷");

		private int value;
		private String message;

		private Status1(int value, String message) {
			this.value = value;
			this.message = message;
		}

		public static String getMessage(int value){
			if(value == 1){
				return TRUE.message;
			}
			return "";
		}
	}

	static enum Status2{
		FALSE(0, "非不良"),
		TRUE(1, "不良");

		private int value;
		private String message;

		private Status2(int value, String message) {
			this.value = value;
			this.message = message;
		}

		public static String getMessage(int value){
			if(value == 1){
				return TRUE.message;
			}
			return "";
		}
	}

	static enum Status3{
		FALSE(0, "非失信被执行人"),
		TRUE(1, "失信被执行人");

		private int value;
		private String message;

		private Status3(int value, String message) {
			this.value = value;
			this.message = message;
		}

		public static String getMessage(int value){
			if(value == 1){
				return TRUE.message;
			}
			return "";
		}
	}

	static enum Status4{
		FALSE(0, "非被执行人"),
		TRUE(1, "被执行人");

		private int value;
		private String message;

		private Status4(int value, String message) {
			this.value = value;
			this.message = message;
		}

		public static String getMessage(int value){
			if(value == 1){
				return TRUE.message;
			}
			return "";
		}
	}

	private static void yourMethod() {
		try {

			Map<Integer, String> map = new LinkedHashMap<>();
			map.put(1, "网贷");
			map.put(10, "不良");
			map.put(11, "不良 网贷");

			map.put(100, "失信被执行人");
			map.put(101, "失信被执行人 网贷");
			map.put(110, "失信被执行人 不良");
			map.put(111, "失信被执行人 不良 网贷");

			map.put(1000, "被执行人");
			map.put(1001, "被执行人 网贷");
			map.put(1010, "被执行人 不良");
			map.put(1011, "被执行人 不良 网贷");
			map.put(1100, "被执行人 失信被执行人");
			map.put(1101, "被执行人 失信被执行人 网贷");
			map.put(1110, "被执行人 失信被执行人 不良");
			map.put(1111, "被执行人 失信被执行人 不良 网贷");

			BytesTest bytes = new BytesTest();

			bytes.setStatus1(1);
			bytes.setStatus2(1);
			bytes.setStatus3(1);

			// 状态4位 有数据为1 无数据为0 ，顺序：被执行人 失信被执行人 不良 网贷
			int subStatus = 0;

			if (bytes.getStatus1() == 1) {
				subStatus = subStatus + 1;
			}

			if (bytes.getStatus2() == 1) {
				subStatus = (subStatus + 10);
			}

			if (bytes.getStatus3() == 1) {
				subStatus = (subStatus + 100);
			}

			if (bytes.getStatus4() == 1) {
				subStatus = (subStatus + 1000);
			}

			System.out.println("状态码：" + subStatus);

			System.out.println("描述： " + map.get(subStatus));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
