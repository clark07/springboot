package com.cs.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class FastJsonDemo {

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
		GenericTypeJsonBean<String, JsonBean> parse = JSON
				.parseObject(json, new TypeReference<GenericTypeJsonBean<String, JsonBean>>(){});
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
		ComplexJsonBean parse = JSON.parseObject(json, ComplexJsonBean.class);
		System.out.println(parse);
	}

	/**
	 * 允许缺少字段
	 * 输出 忽略为空字段
	 */
	private static void losePropertitesBean() {
		System.out.println("---缺失属性演示---");
		String json = "{\"id\":1}";
		System.out.println("json-->"+json);


		JSONObject jsonObject = JSON.parseObject(json);
		System.out.println("jsonObject-->"+jsonObject);

		JsonBean jsonBean = JSON.parseObject(json, JsonBean.class);
		System.out.println("jsonBean-->"+jsonBean);

		System.out.println("fastjson format-->"+JSON.toJSONString(jsonBean, true));
	}

	/**
	 * 允许未知字段
	 * 输出 忽略为空字段
	 */
	private static void addPropertitesBean() {
		System.out.println("---多出其他属性演示---");
		String json = "{\"id\":1, \"other\":\"othervalue\"}";
		System.out.println("json-->"+json);


		JSONObject jsonObject = JSON.parseObject(json);
		System.out.println("jsonObject-->"+jsonObject);

		JsonBean jsonBean = JSON.parseObject(json, JsonBean.class);
		System.out.println("jsonBean-->"+jsonBean);

		System.out.println("fastjson format-->"+JSON.toJSONString(jsonBean, true));
	}

	private static void ArrayBean() {
		System.out.println("---链表演示---");
		String json = "[{\"id\":1,\"name\":\"cs\"},{\"id\":2,\"name\":\"cs2\"}]";
		System.out.println("json-->"+json);


		List<JsonBean> jsonBeans = JSON.parseArray(json, JsonBean.class);
		System.out.println("jsonBeans-->"+jsonBeans);

		System.out.println("fastjson format-->"+JSON.toJSONString(jsonBeans, true));
	}

	private static void singleBean() {
		System.out.println("---简单bean演示---");
		String json = "{\"id\":1,\"name\":\"cs\"}";
		System.out.println("json-->"+json);


		JSONObject jsonObject = JSON.parseObject(json);
		System.out.println("jsonObject-->"+jsonObject);

		JsonBean jsonBean = JSON.parseObject(json, JsonBean.class);
		System.out.println("jsonBean-->"+jsonBean);

		System.out.println("fastjson format-->"+JSON.toJSONString(jsonBean, true));
	}
}
