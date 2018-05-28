package com.kang.convert;

/**
 * @Author：zeqi
 * @Date: Created in 17:30 25/5/18.
 * @Description:
 */
public interface Converter<S,T> {

    T convert(S source);
}
