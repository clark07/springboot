package com.cs.test.http;

import com.cs.test.utils.HttpClientManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by admin on 2016/12/6.
 */
@Configuration
public class HttpConfig {

	@Bean
	public RestTemplate restTemplate(){
		SimpleClientHttpRequestFactory httpClientFactory = new SimpleClientHttpRequestFactory();
		httpClientFactory.setConnectTimeout(30000);
		httpClientFactory.setReadTimeout(30000);

		RestTemplate restTemplate = new RestTemplate(httpClientFactory);

		return restTemplate;
	}


	@Bean
	public HttpClientManager httpClientManager(){
		HttpClientManager client = new HttpClientManager(20000, 20000);
		return client;
	}

}
