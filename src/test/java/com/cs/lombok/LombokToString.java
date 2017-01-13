package com.cs.lombok;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by admin on //.
 */
//@ToString(exclude="id")
@ToString(of="id")
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class LombokToString {

	private String name;
	private int id;


	    public static void main(String[] args){
	      System.out.println(new LombokToString().toString());
	    }
}

