package com.kang.convert;

/**
 * @Author：zeqi
 * @Date: Created in 17:05 25/5/18.
 * @Description:
 */
public interface ConverterFactory<S,R>  {

     <T extends R> Converter<S,T> getConverter(Class<T> targetType);
}
