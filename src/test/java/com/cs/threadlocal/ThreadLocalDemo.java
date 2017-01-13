package com.cs.threadlocal;

/**
 * Created by admin on 2017/1/11.
 */
public class ThreadLocalDemo {


	    public static void main(String[] args){

	    	ThreadLocal tl = new ThreadLocal();
			Object o = tl.get();

		}

}
