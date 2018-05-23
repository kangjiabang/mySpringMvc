package com.kang.annotation;

import java.lang.annotation.*;

/**
 * @Author：zeqi
 * @Date: Created in 09:33 23/5/18.
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
