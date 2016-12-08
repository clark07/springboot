package com.cs.test.utils;

import com.cs.test.exception.HttpClientException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HttpClientManager implements DisposableBean,InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientManager.class);

	private PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
	private HttpClient httpClient;

	private static final String defaultCharsetStr = "UTF-8";
	private static final Charset defaultCharset = Charset.forName(defaultCharsetStr);
	private static final String JsonContentType = "application/json; charset=UTF-8";

	private static final String XmlContentType = "application/xml; charset=UTF-8";

	private static final String UrlencodedContentType = "application/x-www-form-urlencoded; charset=UTF-8";

	private static final Integer BUFFER_SIZE = 4048;

	private ScheduledExecutorService idleConnectionCloseExecutor = Executors.newSingleThreadScheduledExecutor();

	public HttpClientManager(int connectionTimeOut, int soTimeOut) {
		HttpParams params = new SyncBasicHttpParams();
		DefaultHttpClient.setDefaultHttpParams(params);
		HttpConnectionParamBean paramsBean = new HttpConnectionParamBean(params);
		paramsBean.setConnectionTimeout(connectionTimeOut);
		paramsBean.setSoTimeout(soTimeOut);
		httpClient = new DefaultHttpClient(cm, params);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		closeIdleStart();
	}

	public void closeIdleStart() {
		idleConnectionCloseExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				logger.debug("start to close expire and idle connections");
				cm.closeExpiredConnections();
				cm.closeIdleConnections(30, TimeUnit.SECONDS);
			}
		}, 30, 30, TimeUnit.SECONDS);
		logger.info("HttpManager close idel every 30 seconds");
	}

	public void setMaxTotal(int maxTotal) {
		cm.setMaxTotal(maxTotal);
		cm.setDefaultMaxPerRoute(maxTotal);
	}

	public String getStringExec(URI uri) throws IOException {
		HttpGet request = new HttpGet(uri);
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, defaultCharset);
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new IOException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new RuntimeException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public String getStringExec(URI uri, String token) throws IOException {
		HttpGet request = new HttpGet(uri);
		addToken(request, token);
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, defaultCharset);
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new IOException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new RuntimeException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public <T> List<T> getJsonListExec(URI uri, String token, Class<T> clsT) throws HttpClientException {
		HttpGet request = new HttpGet(uri);
		addToken(request, token);
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				List<T> list = JsonUtil.getObjectsFromJson(instream, clsT);
				return list;
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public <T> T getJsonExec(URI uri, String token, Class<T> cls) throws HttpClientException {
		logger.debug("getJsonExec {}", uri);
		HttpGet request = new HttpGet(uri);
		addToken(request, token);
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				T t = JsonUtil.getObjectFromJson(instream, cls);
				return t;
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new HttpClientException("http get json error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get json error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public boolean getExistExec(URI uri, String token) throws HttpClientException {
		HttpGet request = new HttpGet(uri);
		addToken(request, token);
		try {
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();

			if (status == HttpStatus.SC_NOT_FOUND) {
				return false;
			} else if (status == HttpStatus.SC_OK) {
				return true;
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					logger.error("get error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("get error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http get error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}

	}

	public <T> void postJsonExec(URI uri, String token, T obj) throws HttpClientException {
		HttpPost request = new HttpPost(uri);
		addContentType(request, JsonContentType);
		addToken(request, token);

		try {
			String json = JsonUtil.getJsonFromObject(obj);
			StringEntity myEntity = new StringEntity(json, defaultCharsetStr);
			request.setEntity(myEntity);
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (status >= 400) {
				if (entity != null) {
					logger.error("post error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("post error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http post error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public void deleteExec(URI uri, String token) throws HttpClientException {
		logger.debug("deleteExec {}", uri);
		HttpDelete request = new HttpDelete(uri);
		addToken(request, token);
		try {
			HttpResponse response = httpClient.execute(request);
			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (status >= 400) {
				if (entity != null) {
					logger.error("delete error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("delete error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http delete error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http delete error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http delete error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}

	}

	private void addContentType(HttpRequestBase request, String contentType) {
		request.addHeader("Content-Type", contentType);
	}

	private void addToken(HttpRequestBase request, String token) {
		if (!StringUtils.isBlank(token)) {
			try {
				request.addHeader(new BasicHeader("Authorization", "tk=" + URLEncoder.encode(token, "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				logger.error("add token error. ", e);
			}
		}
	}

	@Override
	public void destroy() {
		logger.trace("destory");
		idleConnectionCloseExecutor.shutdown();
		cm.shutdown();
	}

	public InputStream getInputStreamByURI(URI uri) throws HttpClientException {
		HttpGet myGet = new HttpGet(uri);
		try {
			HttpResponse response = httpClient.execute(myGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return entity.getContent();
			} else {
				logger.error("getInputStreamByURI error, status:{}, url:{}",
						new Object[] { response.getStatusLine().getStatusCode(), uri });
				return null;
			}
		} catch (IOException e) {
			throw new HttpClientException(uri.toString() + " get error");
		}
	}

	public InputStream getInputStreamByURL(String url) throws HttpClientException {
		try {
			logger.debug("getInputStreamByURL, url:{}", url);
			URI uri = new URI(url);
			return getInputStreamByURI(uri);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
	}

	public String execGetRequestWithContent(URI uri) throws HttpClientException {
		return execGetRequestWithContent(uri, null);
	}

	public String execGetRequestWithContent(URI uri, String cookie) throws HttpClientException {
		HttpGet request = new HttpGet(uri);
		if (!StringUtils.isBlank(cookie)) {
			request.addHeader(new BasicHeader("Cookie", cookie));
		}

		try {
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();

			if (status == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, defaultCharset);
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					logger.error("get error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("get error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http get error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public String execGetRequestWithParams(URI uri, Map<String, String> params) throws HttpClientException {
		Map<String, String> header = Collections.emptyMap();
		return execGetRequestWithParamsAndHeaders(uri, params, header);
	}

	public String execGetRequestWithParamsAndHeaders(URI uri, Map<String, String> params, Map<String, String> heads)
			throws HttpClientException {
		HttpGet request = new HttpGet(uri);
		if (null != params) {
			for (Entry<String, String> entry : params.entrySet()) {
				request.getParams().setParameter(entry.getKey(), entry.getValue());
			}
		}

		if (heads != null) {
			for (Entry<String, String> entry : heads.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}

		try {
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();

			if (status == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, defaultCharset);
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					logger.error("get error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("get error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http get error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public String execGetRequestWithContent(String url) throws HttpClientException {
		try {
			URI uri = new URI(url);
			return execGetRequestWithContent(uri);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
	}

	public String execGetRequestWithContent(String url, String cookie) throws HttpClientException {
		try {
			URI uri = new URI(url);
			return execGetRequestWithContent(uri, cookie);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
	}

	public boolean execGetRequest(URI uri) throws HttpClientException {
		HttpGet request = new HttpGet(uri);
		try {
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();

			if (status == HttpStatus.SC_OK) {
				return true;
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					logger.error("get error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("get error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http get error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public boolean execGetRequest(String url) throws HttpClientException {
		try {
			URI uri = new URI(url);
			return execGetRequest(uri);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
	}

	public String execGetRequestWithHeader(String url, Map<String, String> header) throws HttpClientException {
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
		Map<String, String> param = Collections.emptyMap();
		return execGetRequestWithParamsAndHeaders(uri, param, header);
	}

	public <T> void execPostJsonRequest(String url, T obj) throws HttpClientException {
		try {
			URI uri = new URI(url);
			execPostJsonRequest(uri, obj);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
	}

	public <T> String execPostJsonRequest(String url, String jsonStr) throws HttpClientException {
		String result = null;
		try {
			URI uri = new URI(url);
			result = execPostJsonRequest(uri, jsonStr);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
		return result;
	}

	public <T> String execPostXMLRequest(String url, String xml) throws HttpClientException {
		String result = null;
		try {
			URI uri = new URI(url);
			result = execPostXMLRequest(uri, xml);
			logger.info(String.format("URL: %s, xml: %s", url, xml));
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
		return result;
	}

	public <T> String execPostUrlencodedRequest(String url, String params) throws HttpClientException {
		String result = null;
		try {
			URI uri = new URI(url);
			result = execPostUrlencodedRequest(uri, params);
			logger.info(String.format("URL: %s, params: %s", url, params));
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
		return result;
	}

	public <T> void execPostJsonRequest(URI uri, T obj) throws HttpClientException {
		HttpPost request = new HttpPost(uri);
		addContentType(request, JsonContentType);

		try {
			String json = JsonUtil.getJsonFromObject(obj);
			StringEntity myEntity = new StringEntity(json, defaultCharsetStr);
			request.setEntity(myEntity);
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (status >= 400) {
				if (entity != null) {
					logger.error("post error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("post error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http post error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public <T> String execPostJsonRequest(URI uri, String jsonStr) throws HttpClientException {
		String result = null;
		HttpPost request = new HttpPost(uri);
		addContentType(request, JsonContentType);

		try {
			StringEntity myEntity = new StringEntity(jsonStr, defaultCharsetStr);
			request.setEntity(myEntity);
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (status >= 400) {
				if (entity != null) {
					logger.error("post error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("post error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http post error. url: " + uri);
			}
			// result = getStringFromHttpEntity(entity);
			result = EntityUtils.toString(entity, defaultCharset);
		} catch (IOException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}
		return result;
	}

	public <T> String execPostUrlencodedRequest(URI uri, String xml) throws HttpClientException {
		String result = null;
		HttpPost request = new HttpPost(uri);
		addContentType(request, UrlencodedContentType);

		try {
			StringEntity myEntity = new StringEntity(xml, defaultCharsetStr);
			request.setEntity(myEntity);
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (status >= 400) {
				if (entity != null) {
					logger.error("post error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("post error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http post error. url: " + uri);
			}

			result = EntityUtils.toString(entity, defaultCharset);
			;
		} catch (IOException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}
		return result;
	}

	public <T> String execPostXMLRequest(URI uri, String xml) throws HttpClientException {
		String result = null;
		HttpPost request = new HttpPost(uri);
		addContentType(request, XmlContentType);

		try {
			StringEntity myEntity = new StringEntity(xml, defaultCharsetStr);
			request.setEntity(myEntity);
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (status >= 400) {
				if (entity != null) {
					logger.error("post error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("post error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http post error. url: " + uri);
			}

			result = EntityUtils.toString(entity, defaultCharset);
			;
		} catch (IOException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http post error. url: " + uri, e);
		} catch (Exception e) {
			throw new HttpClientException("http get json list error. url: " + uri, e);
		} finally {
			request.abort();
		}
		return result;
	}

	public String execPostRequestWithParams(URI uri, Map<String, String> params) throws HttpClientException {
		HttpPost request = new HttpPost(uri);
		if (null != params) {
			for (Entry<String, String> entry : params.entrySet()) {
				request.getParams().setParameter(entry.getKey(), entry.getValue());
			}
		}

		try {
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();

			if (status == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, defaultCharset);
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					logger.error("get error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("get error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http get error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public String execDeleteRequestWithContent(String url) throws HttpClientException {
		try {
			URI uri = new URI(url);
			return execDeleteRequestWithContent(uri, null);
		} catch (URISyntaxException e) {
			throw new HttpClientException(url + " get error", e);
		}
	}

	public String execDeleteRequestWithContent(URI uri, String cookie) throws HttpClientException {
		HttpDelete request = new HttpDelete(uri);
		if (!StringUtils.isBlank(cookie)) {
			request.addHeader(new BasicHeader("Cookie", cookie));
		}

		try {
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();

			if (status == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, defaultCharset);
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					logger.error("get error, status:{}, resonpse:{}, url:{}",
							new Object[] { status, EntityUtils.toString(entity, defaultCharset), uri });
				} else {
					logger.error("get error, status:{}, url:{}", status, uri);
				}
				throw new HttpClientException("http get error. url: " + uri);
			}
		} catch (IOException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new HttpClientException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public String getStringFromHttpEntity(HttpEntity entity) {
		if (entity == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

			String temp = null;

			while ((temp = reader.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

}