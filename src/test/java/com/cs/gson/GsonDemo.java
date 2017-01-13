/*
package com.cs.gson;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.List;

public class GsonDemo {


	private final static Gson gson = new Gson();
	    public static void main(String[] args){
			singleBean();
			ArrayBean();
			losePropertitesBean();
			addPropertitesBean();
			complexBean();
			genericType();
		}

	private static void genericType() {
		System.out.println("---泛型bean演示---");
		String json = "{\"t\":\"t\",\"u\":{\"id\":1,\"name\":\"name\"},\"value\":\"value\"}";
		System.out.println(json);
		GenericTypeJsonBean<String, JsonBean> parse = gson
				.fromJson(json, new TypeToken<GenericTypeJsonBean<String, JsonBean>>(){}.getType());
		String t = parse.getT();
		System.out.println(t.getClass().getName());
		JsonBean u = parse.getU();
		System.out.println(u.getClass().getName());
		System.out.println(parse);
	}

	private static void complexBean() {
		System.out.println("---复杂bean演示---");
		String json = "{\"value\":\"value\",\"jsonBean\":{\"id\":1,\"name\":\"name\"}}";
		System.out.println(json);


		ComplexJsonBean jsonBean = gson.fromJson(json, ComplexJsonBean.class);
		System.out.println("jsonBean-->"+jsonBean);

		System.out.println("gson format-->"+gson.toJson(jsonBean));
	}

	*/
/**
	 * 允许缺少字段
	 * 输出 忽略为空字段
	 *//*

	private static void losePropertitesBean() {
		System.out.println("---缺失属性演示---");
		String json = "{\"id\":1}";
		System.out.println("json-->"+json);

		JsonBean jsonBean = gson.fromJson(json, JsonBean.class);
		System.out.println("jsonBean-->"+jsonBean);

		System.out.println("gson format-->"+gson.toJson(jsonBean));
	}

	*/
/**
	 * 允许未知字段
	 * 输出 忽略为空字段
	 *//*

	private static void addPropertitesBean() {
		System.out.println("---多出其他属性演示---");
		String json = "{\"id\":1, \"other\":\"othervalue\"}";
		System.out.println("json-->"+json);

		JsonBean jsonBean = gson.fromJson(json, JsonBean.class);
		System.out.println("jsonBean-->"+jsonBean);

		System.out.println("gson format-->"+gson.toJson(jsonBean));
	}

	private static void ArrayBean() {
		System.out.println("---链表演示---");
		String json = "[{\"id\":1,\"name\":\"cs\"},{\"id\":2,\"name\":\"cs2\"}]";
		System.out.println("json-->"+json);

		List<JsonBean> jsonBeans = gson.fromJson(json, new TypeToken<List<JsonBean>>(){}.getType());
		System.out.println("jsonBeans-->"+jsonBeans);

		System.out.println("gson format-->"+gson.toJson(jsonBeans));
	}

	private static void singleBean() {
		System.out.println("---简单bean演示---");
		String json = "{\"id\":1,\"name\":\"cs\"}";
		System.out.println("json-->"+json);


		JsonBean jsonBean = gson.fromJson(json, JsonBean.class);
		System.out.println("jsonBean-->"+jsonBean);

		System.out.println("gson format-->"+gson.toJson(jsonBean));
	}
}
*/
