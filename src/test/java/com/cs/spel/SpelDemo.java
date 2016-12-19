package com.cs.spel;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by admin on 2016/12/19.
 */
public class SpelDemo {



	@Test
	public void testSpel(){
		ExpressionParser parser = new SpelExpressionParser();

		EvaluationContext context = new StandardEvaluationContext();

		context.setVariable("list", Stream.iterate(1, i->2*i+1).limit(10).collect(Collectors.toList()));

		Object value = parser.parseExpression("#list").getValue(context);
		System.out.println(value);


	}

}
