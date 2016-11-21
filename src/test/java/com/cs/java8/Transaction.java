package com.cs.java8;

import lombok.Data;

/**
 * Created by admin on 2016/11/12.
 */
@Data
public class Transaction {

	private Trader trader;
	private int year;
	private int value;

	public Transaction(Trader trader, int year, int value) {
		this.trader = trader;
		this.year = year;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Transaction{" + "trader=" + trader + ", year=" + year + ", value=" + value + '}';
	}
}
