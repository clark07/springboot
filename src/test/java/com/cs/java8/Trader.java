package com.cs.java8;

import lombok.Data;

/**
 * Created by admin on 2016/11/12.
 */
@Data
public class Trader {

	private String name;
	private String city;

	public Trader(String name, String city) {
		this.name = name;
		this.city = city;
	}

	@Override
	public String toString() {
		return "Trader{" + "name='" + name + '\'' + ", city='" + city + '\'' + '}';
	}
}
