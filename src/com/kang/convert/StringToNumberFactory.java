package com.kang.convert;

/**
 * @Author：zeqi
 * @Date: Created in 17:05 25/5/18.
 * @Description:
 */
public class StringToNumberFactory  implements  ConverterFactory<String,Number> {


    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return  new StringToNumberConverter<T>(targetType);
    }

    /**
     * 字符串到Number转换器
     */
    public static final class StringToNumberConverter<T extends Number> implements Converter<String,T> {

        private Class<T> targetType;

        public StringToNumberConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            return NumberUtils.parseNumber(source,targetType);
        }


    }



}
