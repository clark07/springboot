package com.cs.lombok;

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on //.
 */

/**
 * lombok.val.flagUsage = [warning | error] (default: not set)
 Lombok will flag any usage of val as a warning or error if configured.
 */
public class LombokVal {

	public static void main(String[] args) {
		example(); example2();
	}

	public static void example() {
		val example = new ArrayList<String>(); example.add("Hello, World!"); val foo = example.get(0);
		System.out.println(foo);
	}

	public static void example2() {
		val map = new HashMap<Integer, String>(); map.put(0, "zero"); map.put(5, "five");
		for (val entry : map.entrySet()) {
			System.out.printf("%d: %s\n", entry.getKey(), entry.getValue());
		}
	}

}
