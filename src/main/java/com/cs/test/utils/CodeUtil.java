package com.cs.test.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;
import java.util.Random;

public class CodeUtil {
	
	/**
	 * 生成唯一的消息Code
	 * @return
	 */
	public static String genernateMessageCode(){
		return String
				.format("%s%s%s", "MSG", DateFormatter.formatDate(new Date(), "yyMMddHHmmss"), StringUtils
						.leftPad(new Random().nextInt(100)+"", 2, '0'));
	}
	public static long genernateOrderId(){
		String temp = String
				.format("%s%s%s", "109", DateFormatter.formatDate(new Date(), "yyMMddHHmmss"), StringUtils.leftPad(new Random().nextInt(1000)+"", 3, '0'));
		return NumberUtils.toLong(temp);
	}
}
