package com.cs.test.annotation;

import java.lang.annotation.*;

/**
 * Created by admin on 2016/12/15.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JerseyResource {
}
