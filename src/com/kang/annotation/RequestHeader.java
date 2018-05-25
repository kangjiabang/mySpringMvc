package com.kang.annotation;

/**
 * @Author：zeqi
 * @Date: Created in 14:38 23/5/18.
 * @Description:
 */

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHeader {

     String name() default "";

     String defaultValue() default "";

     boolean required() default true;

}
