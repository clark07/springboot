package com.cs.java8;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by admin on 2016/11/14.
 */
public class ConsiderDemo {
	static List<String> list = Stream.iterate(1, i->i+1).limit(22).map(String::valueOf).collect(Collectors.toList());


	    public static void main(String[] args){

			long t1 = System.currentTimeMillis();
			method1();
			long t2 = System.currentTimeMillis();
			System.out.println(String.format("method1-->cost:%sms", (t2-t1)));

			method12();
			long t3 = System.currentTimeMillis();
			System.out.println(String.format("method12-->cost:%sms", (t3-t2)));

			method13();
			long t4 = System.currentTimeMillis();
			System.out.println(String.format("method13-->cost:%sms", (t4-t3)));

			method14();
			long t5 = System.currentTimeMillis();
			System.out.println(String.format("method14-->cost:%sms", (t5-t4)));

			method3();
			long t6 = System.currentTimeMillis();
			System.out.println(String.format("method3-->cost:%sms", (t6-t5)));

		}

	private static void method3() {
		List<List<String>> result = getAllCombo(list);
		//System.out.println(result);
	}

	private static List<List<String>> getAllCombo(List<String> list) {
		List<List<String>> result = new ArrayList<>();
		if(list.size() == 0){
			result.add(new ArrayList<>());
			return result;
		}


		String first = list.get(0);
		List<String> subList = list.subList(1, list.size());

		List<List<String>> subAllCombo = getAllCombo(subList);
		List<List<String>> subCombo2 = fetchCombo(first, subAllCombo);

		result.addAll(subAllCombo);
		result.addAll(subCombo2);
		return result;
	}

	private static List<List<String>> fetchCombo(String first, List<List<String>> subAllCombo) {
		List<List<String>> result = new ArrayList<>();
		for (List<String> strings : subAllCombo) {
			List<String> subResult = new ArrayList<>();
			subResult.add(first);
			subResult.addAll(strings);
			result.add(subResult);
		}
		return result;
	}

	private static void method1() {
		int size = list.size();
		List<List<String>> result = new ArrayList<>(2<<size);

		for (int i = 0; i < 2<<(size-1); i++) {
			char[] chars = StringUtils.reverse(StringUtils.leftPad(Integer.toBinaryString(i), size)).toCharArray();
			List<String> single = new ArrayList<>();

			for (int j = 0; j < chars.length; j++) {
				if(chars[j] == '1'){
					single.add(list.get(j));
				}
			}
			result.add(single);
		}
		//System.out.println(result);
	}
	private static void method12() {
		int size = list.size();
		List<List<String>> result = new ArrayList<>(2<<size);
		for (int i = 0; i < 2<<(size-1); i++) {
			char[] chars = getBinStr(i, size).toCharArray();
			List<String> single = new ArrayList<>();

			for (int j = 0; j < chars.length; j++) {
				if(chars[j] == '1'){
					single.add(list.get(j));
				}
			}
			result.add(single);
		}
		//System.out.println(result);
	}
	private static void method13() {
		int size = list.size();
		List<List<String>> result = new ArrayList<>(2<<size);

		for (int i = 0; i < 2<<(size-1); i++) {
			char[] chars = getBinStr(i, size).toCharArray();
			List<String> single = new ArrayList<>(size);

			for (int j = 0; j < chars.length; j++) {
				if(chars[j] == '1'){
					single.add(list.get(j));
				}
			}
			result.add(single);
		}
		//System.out.println(result);
	}
	private static void method14() {

		long t0 = System.currentTimeMillis();
		int size = list.size();
		List<List<String>> result = new ArrayList<>(2<<size);

		long t1 = System.currentTimeMillis();
		System.out.println(String.format("method14-1-->cost:%sms", (t1-t0)));
		for (int i = 0; i < 2<<(size-1); i++){
			result.add(new ArrayList<>(size));
		}

		long t2 = System.currentTimeMillis();
		System.out.println(String.format("method14-2-->cost:%sms", (t2-t1)));
		for (int i = 0; i < 2<<(size-1); i++){
			int index = 0;
			while(1<=i>>index){
				if((i>>index&1)>0){
					result.get(i).add(list.get(index));
				}
				index++;
			}
		}

		long t3 = System.currentTimeMillis();
		System.out.println(String.format("method14-3-->cost:%sms", (t3-t2)));

		//System.out.println(result);
	}

	private static String getBinStr(int i, int size) {
		StringBuffer sb = new StringBuffer();

		while(i>0){
			sb.append(i%2==1?1:0);
			i>>=1;
		}
		return sb.append(repeate("0", size-sb.length())).toString();


	}
	private static String repeate(String ele, int n) {
		if(n==0){return "";}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			sb.append(ele);
		}
		return sb.toString();
	}




}
