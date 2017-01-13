package com.cs.poi;

import java.lang.annotation.*;

/**
 * Created by admin on 2017/1/9.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
	String value() default "";
	int sort() default 0;
}
