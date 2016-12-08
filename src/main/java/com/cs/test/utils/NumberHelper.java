package com.cs.test.utils;

import java.math.BigDecimal;

public class NumberHelper {

	/**
	 * 校验>=0
	 */
	public static boolean validate(Integer i) {
		if(i != null && i >= 0 ){
			return true;
		}
		return false;
	}

	/**
	 * 校验>=0
	 */
	public static boolean validate(Long l) {
		if(l != null && l >= 0 ){
			return true;
		}
		return false;
	}

	/**
	 * 校验>=0
	 */
	public static boolean validate(BigDecimal bigDecimal) {
		if(bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) >= 0){
			return true;
		}
		return false;
	}

	/**
	 * 校验>0
	 */
	public static boolean validatePositive(Integer i) {
		if(i != null && i > 0 ){
			return true;
		}
		return false;
	}
	/**
	 * 校验>0
	 */
	public static boolean validatePositive(Long l) {
		if(l != null && l > 0 ){
			return true;
		}
		return false;
	}
	/**
	 * 校验>0
	 */
	public static boolean validatePositive(BigDecimal bigDecimal) {
		if(bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) > 0){
			return true;
		}
		return false;
	}

}
