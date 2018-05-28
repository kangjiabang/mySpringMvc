package com.kang.convert;

/**
 * @Author：zeqi
 * @Date: Created in 14:33 27/5/18.
 * @Description:
 */
public interface GenericConverter {


    Object convert(Object source, Class sourceType, Class dstType);
}
