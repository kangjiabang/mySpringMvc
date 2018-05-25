package com.kang.annotation;

/**
 * @Author：zeqi
 * @Date: Created in 14:38 23/5/18.
 * @Description:
 */

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWired {

     String name() default "";

}
