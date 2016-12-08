package com.cs.test.utils;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * md5 加密方法
 * 
 * @author twk
 *
 */
public class UrlUtil {

	static Logger logger = Logger.getLogger(UrlUtil.class);

	public static String encode(String str) {
		StringBuilder sb = new StringBuilder();
		char[] chars = str.toCharArray();
		for (char ch : chars) {
			String encodeCh = "";
			try {
				encodeCh = URLEncoder.encode(String.valueOf(ch), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (encodeCh.length() > 1) {
				sb.append(encodeCh.toLowerCase());
			} else {
				sb.append(encodeCh);
			}
		}

		return sb.toString();
	}
}
