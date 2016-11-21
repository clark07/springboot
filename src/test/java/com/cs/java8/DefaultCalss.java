package com.cs.java8;

/**
 * Created by admin on 2016/11/14.
 */
public class DefaultCalss {


	    public static void main(String[] args){

	    }


	static interface face1{
		int getA(int a);
		int getB();
		default void print(){
			System.out.println("face1");
		}
	}
	static interface face2{
		int getA(String a);
		int getC();
		default void print(){
			System.out.println("face2");
		}
	}

	static class face3 implements face1, face2 {
		@Override
		public int getA(int a) {
			return 0;
		}

		@Override
		public int getB() {
			return 0;
		}

		@Override
		public void print() {
			face2.super.print();
		}

		@Override
		public int getA(String a) {
			return 0;
		}

		@Override
		public int getC() {
			return 0;
		}
	}
}
