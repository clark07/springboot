package com.cs.jetbrains;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by admin on 2016/11/15.
 */
public class JetBrains1603Demo {


	public static void main(String[] args) {


		List<String> list = IntStream.rangeClosed(1, 100).mapToObj(String::valueOf).filter(s -> s.contains("1"))
									 .filter(s -> s.contains("2")).collect(Collectors.toList());


		System.out.println(list);


	}


}
