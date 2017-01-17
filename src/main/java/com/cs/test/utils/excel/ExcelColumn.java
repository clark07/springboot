package com.cs.test.utils.excel;


import java.lang.annotation.*;

/**
 * Created by admin on 2017/1/9.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
	String value() default "";

	/**
	 *从1开始
	 * @return
	 */
	int col() default 0;
}
