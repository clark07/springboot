package com.cs.test.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by admin on 2016/12/6.
 */
public class HttpUtils {

	private final static String DEFAULT_ENCODING = "UTF-8";

	public static String execGet(String url) {
		return execGet(url, DEFAULT_ENCODING);
	}

	public static String execGet(String url, String encoding) {
		try {
			HttpGet get = new HttpGet(url);
			CloseableHttpClient aDefault = HttpClients.createDefault();
			CloseableHttpResponse response = aDefault.execute(get);

			return EntityUtils.toString(response.getEntity(), encoding);
		} catch (Exception ignore) {
		} return "";
	}
}
