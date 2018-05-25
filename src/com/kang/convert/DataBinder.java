package com.kang.convert;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @Author：zeqi
 * @Date: Created in 16:39 24/5/18.
 * @Description:
 */
public class DataBinder {

    public Object convertIfNecessary(Object arg, Parameter parameter) {
        Class<?> sourceType = arg.getClass();
        Class<?> dstType = parameter.getType();
        if (sourceType == dstType) {
            return arg;
        }
        if (sourceType == String.class && dstType == int.class) {
            return Integer.decode((String)arg).intValue();
        }
        //arg.
        return null;
    }
}
