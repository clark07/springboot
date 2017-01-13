package com.cs.lombok;

import lombok.val;

/**
 * Created by admin on 2016/12/26.
 */
public class LombokDemo {


	    public static void main(String[] args){

			val data = LombokData.builder().id(1).name("name").build();

System.out.println(data);
		}
}
