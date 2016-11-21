package com.cs.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by admin on 2016/11/12.
 */
public class TransactionDemo {


	public static void main(String[] args) {

		Trader raoul = new Trader("raoul", "Cam");
		Trader mario = new Trader("mario", "Milan");
		Trader alan = new Trader("alan", "Cam");
		Trader brian = new Trader("brian", "Cam");

		List<Transaction> transactions = Stream
				.of(new Transaction(brian, 2011, 300), new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400), new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950))
				.collect(Collectors.toList());


		//(1)
		transactions.stream().filter(t->t.getYear()==2011).sorted(Comparator.comparing(Transaction::getValue))
					.forEach(System.out::println);

		//2
		transactions.stream().map(Transaction::getTrader).map(Trader::getCity).distinct().forEach(System.out::println);
		transactions.stream().map(t-> t.getTrader().getCity()).distinct()
					.forEach(System.out::println);
		transactions.stream().collect(HashSet::new,(set, t)->set.add(t.getTrader().getCity()), HashSet::addAll)
					.forEach(System.out::println);

		//3
		transactions.stream().map(Transaction::getTrader).filter(t->t.getCity().equalsIgnoreCase("Cam")).distinct().sorted(Comparator.comparing(Trader::getName))
					.forEach(System.out::println);

		//4
		transactions.stream().map(Transaction::getTrader).distinct().map(Trader::getName).sorted()
					.forEach(System.out::println);
		transactions.stream().map(Transaction::getTrader).distinct().map(Trader::getName).sorted().collect(Collectors.joining(","))
					;

		//5
		transactions.stream().anyMatch(t->t.getTrader().getCity().equals("Milan"));

		//6
		transactions.stream().filter(t->t.getTrader().getCity().equals("Cam")).map(Transaction::getValue).reduce((v1,v2)->v1+v2)
					.ifPresent(System.out::println);

		//7
		transactions.stream().max(Comparator.comparing(Transaction::getValue))
					.ifPresent(t->System.out.println(t.getValue()));

		//8
		transactions.stream().min(Comparator.comparing(Transaction::getValue))
					.ifPresent(System.out::println);


		IntStream.rangeClosed(1, 100).boxed().flatMap(a->
			IntStream.rangeClosed(a, 100).boxed().map(b-> Arrays.asList(a.doubleValue(), b.doubleValue(), Math.sqrt(a*a+b*b)))


		).filter(d->d.get(2)%1==0).forEach(d-> System.out.println(d));



		Stream.iterate(new int[]{0, 1}, ints -> {

			int a0 = ints[0]+ints[1];
			//int a1 = ints[1]+a0;
			return new int[]{ints[1], a0};
		}).limit(10).forEach(ints -> System.out.println(String.format("(%s,%s)", ints[0], ints[1])));

	}


}
