package com.cs.test.utils;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 简化版httpClient
 * @author admin
 *
 */
public class HttpSimpleUtils {

	private static final Logger logger = Logger
			.getLogger(HttpSimpleUtils.class);
	private int readTimeOut = 30000;
	private int connectionTimeOut = 30000;
	public HttpSimpleUtils() {
	}
	public HttpSimpleUtils(int connectionTimeOut, int readTimeOut ){
		this.connectionTimeOut = connectionTimeOut;
		this.readTimeOut = readTimeOut;
	}
	
	
	public String execGet(String uri){
		InputStream is = null;
		BufferedReader br = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpURLConnection.setConnectTimeout(connectionTimeOut);
			httpURLConnection.setReadTimeout(readTimeOut);
			
			int status = httpURLConnection.getResponseCode();
			if(status == HttpStatus.SC_OK){
				is = httpURLConnection.getInputStream();
				br = new BufferedReader(new InputStreamReader(is));
				StringBuffer buf = new StringBuffer();
				
				String temp = "";
				while((temp = br.readLine()) != null){
					buf.append(temp);
				}
				return buf.toString();
			}
		} catch (Exception e) {
			logger.error(String.format("execGet:%s exception", uri), e);
		} finally{
			try {
				if(br != null) br.close();
				if(is != null) is.close();
			} catch (Exception ignore) {
			}
			
		}
		
		return "";
	}
	

}
