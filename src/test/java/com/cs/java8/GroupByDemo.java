package com.cs.java8;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by admin on 2016/11/13.
 */
public class GroupByDemo {


	public static void main(String[] args) {

		List<GroupBean> groupBeenList = Arrays
				.asList(new GroupBean("a1", "b1", 1), new GroupBean("a1", "b2", 2), new GroupBean("a1", "b2", 3), new GroupBean("a2", "b4", 1));
		Map<String, Map<String, List<GroupBean>>> groupGroupMap = groupBeenList.stream().collect(Collectors
				.groupingBy(GroupBean::getA, Collectors.groupingBy(GroupBean::getB)));
		System.out.println(groupGroupMap);

		Map<String, Long> groupCountMap = groupBeenList.stream().collect(Collectors
				.groupingBy(GroupBean::getA, Collectors.counting()));
		System.out.println(groupCountMap);

		Map<String, GroupBean> groupMaxMap = groupBeenList.stream().collect(Collectors
				.groupingBy(GroupBean::getA, Collectors.collectingAndThen(Collectors
						.maxBy(Comparator.comparingInt(GroupBean::getValue)), Optional::get)));

		System.out.println(groupMaxMap);

		Map<Boolean, List<GroupBean>> groupPartitionMap = groupBeenList.stream().collect(Collectors
				.partitioningBy(gb -> gb.getValue() >= 2));
		System.out.println(groupPartitionMap);

		TreeSet<String> mapColletMap = groupBeenList.stream().map(GroupBean::getA)
											   .collect(Collectors.toCollection(TreeSet::new));
		System.out.println(mapColletMap);

	}

	@Data
	static class GroupBean {
		private String a;
		private String b;
		private int value;

		public GroupBean(String a, String b, int value) {
			this.a = a; this.b = b; this.value = value;
		}

		@Override
		public String toString() {
			return "GroupBean{" + "a='" + a + '\'' + ", b='" + b + '\'' + ", value=" + value + '}';
		}
	}

}
