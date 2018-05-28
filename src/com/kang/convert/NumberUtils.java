package com.kang.convert;

/**
 * @Author：zeqi
 * @Date: Created in 18:16 25/5/18.
 * @Description:
 */
public class NumberUtils {

    /**
     *  字符串解析成Number类型
     * @param source
     * @param targetType
     * @return
     */
    public static <T extends Number> T parseNumber(String source,Class<T> targetType) {
        if (targetType == Integer.class) {
            return (T) Integer.decode(source);
        } if (targetType == Long.class) {
            return (T) Long.decode(source);
        }
        return  null;
    }
}
