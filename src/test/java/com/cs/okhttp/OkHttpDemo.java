package com.cs.okhttp;


import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/1/12.
 */
public class OkHttpDemo {
	private final static ConnectionPool cp = new ConnectionPool(20, 5, TimeUnit.MINUTES);
	private final static OkHttpClient client = new OkHttpClient.Builder().connectionPool(cp).readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).build();

	public static void main(String[] args) {
		String getUrl = "http://httpbin.org/get";
		System.out.println(get(getUrl));

		String postUrl = "http://httpbin.org/post";
		System.out.println(postJson(postUrl, JSON.toJSONString(Arrays.asList("s1", "s2"), true)));
	}


	public static String get(String url) {
		String result = "";
		Request request = new Request.Builder().url(url).build();
		try {
			Response response = client.newCall(request).execute();
			result = response.body().string();
		} catch (Exception e) {
		}

		return result;
	}
	public static String postJson(String url, String json) {
		String result = "";
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);

		Request request = new Request.Builder().url(url).post(requestBody).build();
		try {
			Response response = client.newCall(request).execute();
			result = response.body().string();
		} catch (Exception e) {
		}

		return result;
	}


}
