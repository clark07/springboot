package com.cs.test.utils;

import java.math.BigInteger;


public class MathUtil {
	
	public static BigInteger factorial(long x) {
		BigInteger result = BigInteger.valueOf(1);
		for (long i = 2; i <= x; i++) {
			result = result.multiply(BigInteger.valueOf(i));;
		}
		return result;
	}
	
	public static long combination(long n, long k) {
		return factorial(n).divide(factorial(k).multiply(factorial(n - k))).longValue();
	}
	
	public static long permutation(long n, long k) {
		return factorial(n).divide(factorial(n - k)).longValue();
	}

}
