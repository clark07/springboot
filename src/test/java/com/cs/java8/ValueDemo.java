package com.cs.java8;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/12/8.
 */
public class ValueDemo {


	    public static void main(String[] args){

	    	double initValue = 0.8;
	    	int historyDay = 8;
	    	int avgDay = 22;
	    	int avgValue = 1;

			for (int i = 1; i <= 14; i++) {

				if(i>=avgDay){
					System.out.println(String.format("%s->%s", i, avgValue));
				}else if(i+historyDay>=avgDay){
					System.out.println(String.format("%s->%s", i,new BigDecimal((avgDay*avgValue -(avgDay-i)*initValue)/i).setScale(1, BigDecimal.ROUND_HALF_EVEN)));
				}else{
					System.out.println(String.format("%s->%s", i, new BigDecimal((avgDay*avgValue -historyDay*initValue)/i).setScale(1, BigDecimal.ROUND_HALF_EVEN)));
				}
			}


	    }




}
