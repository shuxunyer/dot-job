package com.dot.job.core.handle.annotation;

import java.lang.annotation.*;



@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DotJobHandle {
    String value() default "";
}
