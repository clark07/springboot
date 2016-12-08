package com.cs.test.utils;


import java.util.Random;

public class StringUtil {

	private final static String NUM_POOL = "0123456789";
	private final static String CHAR_POOL = "abcdefghijklmnopqrstuvwxyz";
	private final static String WORD_POOL = "0123456789abcdefghijklmnopqrstuvwxyz";
	private final static String PASSWORD_POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** 空字符串 */
	public static final String EMPTY_STRING = "";
	private static final String FILE_SEPARATOR = "/";

	public static String getRandNumber(int minlen, int maxlen) {
		String s = "";
		if (minlen > maxlen) {
			minlen = maxlen;
		}

		Random random = new Random();
		int len = minlen + random.nextInt(maxlen - minlen + 1);

		for (int i = 0; i < len; i++) {
			s += NUM_POOL.charAt(random.nextInt(NUM_POOL.length()));
		}

		return s;
	}
	
	public static String getRandPassword(int minlen, int maxlen) {
		StringBuffer sb = new StringBuffer();
		if (minlen > maxlen) {
			minlen = maxlen;
		}
		
		Random random = new Random();
		int len = minlen + random.nextInt(maxlen - minlen + 1);
		
		for (int i = 0; i < len; i++) {
			sb.append(PASSWORD_POOL.charAt(random.nextInt(PASSWORD_POOL.length())));
		}
		
		return sb.toString();
	}
	public static String getRandWord(int minlen, int maxlen) {
		StringBuffer sb = new StringBuffer();
		if (minlen > maxlen) {
			minlen = maxlen;
		}
		
		Random random = new Random();
		int len = minlen + random.nextInt(maxlen - minlen + 1);
		
		for (int i = 0; i < len; i++) {
			sb.append(WORD_POOL.charAt(random.nextInt(WORD_POOL.length())));
		}
		
		return sb.toString();
	}
	public static String getRandChar(int minlen, int maxlen) {
		StringBuffer sb = new StringBuffer();
		if (minlen > maxlen) {
			minlen = maxlen;
		}
		
		Random random = new Random();
		int len = minlen + random.nextInt(maxlen - minlen + 1);
		
		for (int i = 0; i < len; i++) {
			sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
		}
		
		return sb.toString();
	}

	 public static void main (String[] args)
	 {
	 }

}
