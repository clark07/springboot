package com.cs.test.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtil {

	private final static Pattern p = Pattern.compile("\\\\u[a-f0-9]{4}");
	public static void main(String[] args) {
		String content = "{\"result\":true,\"flag\":1,\"message\":\"\\u6210\\u529f\",\"data\":[]}";
		System.out.println(convertUnicode2String(content));

	}

	
	public static String convertUnicode2String(String content){
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(content)){
			Matcher m = p.matcher(content);
			
			while(m.find()){
				String find = m.group();
				
				m.appendReplacement(sb, unicode2String(find));
			}
			m.appendTail(sb);
		}
		
		return sb.toString();
	}
	
	/**
	 * 字符串转换unicode
	 */
	private static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	private static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/**
	 * 过滤emoji字符
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll(
					"[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		} else {
			return source;
		}
	}
}
