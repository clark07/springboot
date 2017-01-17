package com.cs.threadlocal;

import com.cs.test.utils.DateFormatter;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 2017/1/13.
 */
public class ExecutorServiceDemo {


	    public static void main(String[] args){
			//cacheDemo();
			//fixedDemo();
			scheduleDemo();
			//singleDemo();
	    }

	private static void singleDemo() {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		AtomicInteger ai = new AtomicInteger();
		for (int i = 0; i < 5; i++) {
			singleThreadExecutor.execute(()->{
				int index = ai.incrementAndGet();

				System.out.println(String.format("singleDemo %s begin exec", index));
				try {
					Thread.sleep(1000);
				}catch (Exception e){

				}
				System.out.println(String.format("singleDemo %s end exec", index));
			});
		}

		singleThreadExecutor.shutdown();
	}

	private static void scheduleDemo() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
		/*AtomicInteger ai = new AtomicInteger();
		for (int i = 0; i < 5; i++) {
			scheduledExecutorService.execute(()->{
				int index = ai.incrementAndGet();

				System.out.println(String.format("scheduleDemo %s begin exec", index));
				try {
					Thread.sleep(1000);
				}catch (Exception e){

				}
				System.out.println(String.format("scheduleDemo %s end exec", index));
			});
		}*/

		//两次执行的间隔时间

/*		scheduledExecutorService.scheduleAtFixedRate(()->{
			System.out.println(DateFormatter.longDate(new Date()));
			try {
				Thread.sleep(3000);
			}catch (Exception e){

			}
		}, 1000, 2000, TimeUnit.MILLISECONDS);*/

//任务中止时间与开始时间的间隔
		scheduledExecutorService.scheduleWithFixedDelay(()->{
			System.out.println(DateFormatter.longDate(new Date()));
			try {
				Thread.sleep(3000);
			}catch (Exception e){
			}
		}, 1000, 2000, TimeUnit.MILLISECONDS);

		//scheduledExecutorService.shutdown();

	}

	private static void fixedDemo() {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		AtomicInteger ai = new AtomicInteger();
		for (int i = 0; i < 5; i++) {
			fixedThreadPool.execute(()->{
				int index = ai.incrementAndGet();

				System.out.println(String.format("fixedDemo %s begin exec", index));
				try {
					Thread.sleep(1000);
				}catch (Exception e){

				}
				System.out.println(String.format("fixedDemo %s end exec", index));
			});
		}
		fixedThreadPool.shutdown();
	}

	private static void cacheDemo() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		AtomicInteger ai = new AtomicInteger();
		for (int i = 0; i < 5; i++) {
			cachedThreadPool.execute(()->{
				int index = ai.incrementAndGet();

				System.out.println(String.format("cacheDemo %s begin exec", index));
				try {
					Thread.sleep(1000);
				}catch (Exception e){

				}
				System.out.println(String.format("cacheDemo %s end exec", index));
			});
		}

		cachedThreadPool.shutdown();
	}


}
