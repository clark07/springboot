package com.cs.test.utils;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.ToString;
import okhttp3.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by admin on 2016/12/6.
 */
public class HttpUtils {

	private final static Logger log = LoggerFactory.getLogger(HttpUtils.class);

	public final static String GET = "GET";
	public final static String POST = "POST";
	public final static String PUT = "PUT";
	public final static String DELETE = "DELETE";
	public final static String PATCH = "PATCH";

	private final static String UTF8 = "UTF-8";
	private final static String GBK = "GBK";

	private final static String DEFAULT_CHARSET = UTF8;
	private final static String DEFAULT_METHOD = GET;
	private final static String DEFAULT_MEDIA_TYPE = javax.ws.rs.core.MediaType.APPLICATION_JSON;
	private final static boolean DEFAULT_LOG = false;

	private final static OkHttpClient client = new OkHttpClient.Builder()
			.connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
			.readTimeout(20, TimeUnit.SECONDS)
			.connectTimeout(20, TimeUnit.SECONDS).build();

	public static void main(String[] args){
		Map<String, String> map = new HashMap<>();
		map.put("k", "v");
		execute(OkHttp.builder().url("http://httpbin.org/get?k1=v1").method(GET).headerMap(map).queryMap(map).requestLog(true).responseLog(true).build());
		execute(OkHttp.builder().url("http://httpbin.org/post?k1=v1").method(POST).data("{\"id\":\"1\"}").mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).headerMap(map).queryMap(map).requestLog(true).responseLog(true).build());
		execute(OkHttp.builder().url("http://httpbin.org/put?k1=v1").method(PUT).data("{\"id\":\"1\"}").mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).headerMap(map).queryMap(map).requestLog(true).responseLog(true).build());
		execute(OkHttp.builder().url("http://httpbin.org/delete?k1=v1").method(DELETE).data("{\"id\":\"1\"}").mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).headerMap(map).queryMap(map).requestLog(true).responseLog(true).build());
		execute(OkHttp.builder().url("http://httpbin.org/patch?k1=v1").method(PATCH).data("{\"id\":\"1\"}").mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).headerMap(map).queryMap(map).requestLog(true).responseLog(true).build());
	}

	/**
	 * GET请求
	 * @param url
	 * URL地址
	 * @return
	 */
	public static String get(String url){
		return execute(OkHttp.builder().url(url).build());
	}
	/**
	 * GET请求
	 * @param url
	 * URL地址
	 * @return
	 */
	public static String get(String url, String charset){
		return execute(OkHttp.builder().url(url).responseCharset(charset).build());
	}

	/**
	 * 带查询参数的GET查询
	 * @param url
	 * URL地址
	 * @param queryMap
	 * 查询参数
	 * @return
	 */
	public static String get(String url,  Map<String, String> queryMap){
		return execute(OkHttp.builder().url(url).queryMap(queryMap).build());
	}

	/**
	 * 带查询参数的GET查询
	 * @param url
	 * URL地址
	 * @param queryMap
	 * 查询参数
	 * @return
	 */
	public static String get(String url,  Map<String, String> queryMap, String charset){
		return execute(OkHttp.builder().url(url).queryMap(queryMap).responseCharset(charset).build());
	}

	/**
	 * POST
	 * application/json
	 * @param url
	 * @param obj
	 * @return
	 */
	public static String postJson(String url, Object obj){
		return execute(OkHttp.builder().url(url).method(POST).data(JSON.toJSONString(obj)).mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).build());
	}

	/**
	 * POST
	 * application/x-www-form-urlencoded
	 * @param url
	 * @param formMap
	 * @return
	 */
	public static String postForm(String url, Map<String, String> formMap){
		String data = "";
		if(MapUtils.isNotEmpty(formMap)){
			data = formMap.entrySet().stream().map(entry->String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors
					.joining("&"));
		}
		return execute(OkHttp.builder().url(url).method(POST).data(data).mediaType(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED).build());
	}

	private static String post(String url, String data, String mediaType, String charset){
		return execute(OkHttp.builder().url(url).method(POST).data(data).mediaType(mediaType).responseCharset(charset).build());
	}

	/**
	 * 通用执行方法
	 */
	private static String execute(OkHttp okHttp){
		if(StringUtils.isBlank(okHttp.requestCharset)){
			okHttp.requestCharset = DEFAULT_CHARSET;
		}
		if(StringUtils.isBlank(okHttp.responseCharset)){
			okHttp.responseCharset = DEFAULT_CHARSET;
		}
		if(StringUtils.isBlank(okHttp.method)){
			okHttp.method = DEFAULT_METHOD;
		}
		if(StringUtils.isBlank(okHttp.mediaType)){
			okHttp.mediaType = DEFAULT_MEDIA_TYPE;
		}
		if(okHttp.requestLog){//记录请求日志
			log.info(okHttp.toString());
		}

		String url =okHttp.url;

		Request.Builder builder = new Request.Builder();

		if(MapUtils.isNotEmpty(okHttp.queryMap)){
			String queryParams = okHttp.queryMap.entrySet().stream()
									 .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
									 .collect(Collectors.joining("&"));
			url = String.format("%s%s%s", url, url.contains("?")?"&":"?", queryParams);
		}
		builder.url(url);

		if(MapUtils.isNotEmpty(okHttp.headerMap)){
			okHttp.headerMap.forEach(builder::addHeader);
		}

		String method = okHttp.method.toUpperCase();
		String mediaType = String.format("%s;charset=%s", okHttp.mediaType, okHttp.requestCharset);

		if(StringUtils.equals(method, GET)){
			builder.get();
		}else if(ArrayUtils.contains(new String[]{POST, PUT, DELETE, PATCH}, method)){
			RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), okHttp.data);
			builder.method(method, requestBody);
		}else{
			throw new NotSupportedException(String.format("http method:%s not support!", method));
		}

		String result = "";
		try {
			Response response = client.newCall(builder.build()).execute();
			byte[] bytes = response.body().bytes();
			result = new String(bytes, okHttp.responseCharset);
			if (okHttp.responseLog){//记录返回日志
				log.info(String.format("Got response->%s",result));
			}
		}catch (Exception e){
			log.error(okHttp.toString(), e);
		}
		return result;
	}

	@Builder
	@ToString(exclude = {"requestCharset", "responseCharset", "requestLog", "responseLog" })
	static class OkHttp{
		private String url;
		private String method = DEFAULT_METHOD;
		private String data;
		private String mediaType = DEFAULT_MEDIA_TYPE;
		private Map<String, String> queryMap;
		private Map<String, String> headerMap;
		private String requestCharset = DEFAULT_CHARSET;
		private boolean requestLog = DEFAULT_LOG;

		private String responseCharset = DEFAULT_CHARSET;
		private boolean responseLog = DEFAULT_LOG;
	}
}
