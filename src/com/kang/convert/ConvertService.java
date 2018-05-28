package com.kang.convert;

import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：zeqi
 * @Date: Created in 16:39 24/5/18.
 * @Description:
 */
public class ConvertService {

    /**
     * 保存参数类型对和Converter 之间的映射关系
     */
    private static Map<ConvertiblePair, ConverterForPair> srcDstTypePairConverterMap = new ConcurrentHashMap<>();

    static {
        srcDstTypePairConverterMap.put(new ConvertiblePair(String.class, Boolean.class),
                buildConvertPair(new ConverterAdapter(new StringToBooleanConverter(),
                        String.class, Boolean.class)));
        srcDstTypePairConverterMap.put(new ConvertiblePair(String.class, Character.class),
                buildConvertPair(new ConverterAdapter(new StringToCharacterConverter(),
                        String.class, Character.class)));


        /*srcDstTypePairConverterMap.put(new ConvertiblePair(String.class, Collection.class),
                buildConvertPair(new ConverterAdapter(new StringToCollectionConverter(),
                        String.class, Collection.class)));*/

        srcDstTypePairConverterMap.put(new ConvertiblePair(String.class, Number.class),
                buildConvertPair(new ConverterFactoryAdapter(new StringToNumberFactory(),
                        String.class, Character.class)));
    }


    private static ConverterForPair buildConvertPair(GenericConverter genericConverter) {
        return new ConverterForPair(genericConverter);
    }


    public Object convert(Object arg, Parameter parameter) {
        Class sourceType = arg.getClass();
        Class dstType = parameter.getType();
        if (sourceType == dstType) {
            return arg;
        }
        ConvertService.ConverterForPair converterForPair = getConverterForPair(sourceType, dstType);

        if (converterForPair != null) {
            return converterForPair.getGenericConverter().convert(arg, sourceType, dstType);
        }
//arg.
        return null;

    }
    /**
     * @param sourceType
     * @param dstType
     * @return
     */
    public ConverterForPair getConverterForPair(Class sourceType, Class dstType) {
        ConverterForPair converterForPair = srcDstTypePairConverterMap.get(new ConvertiblePair(sourceType, dstType));

        //如果converterForPair 为空，查找dstType的父类，因为String -> Integer的转化类为 String -> Number
        if (converterForPair == null) {
            Class superDstType = dstType.getSuperclass();
            if (superDstType != null) {
                converterForPair = getConverterForPair(sourceType, superDstType);
            }

        }
        return converterForPair;
    }

private static final class ConvertiblePair {
    /**
     * 原类型
     */
    private Class sourceType;

    /**
     * 目标类型
     */
    private Class dstType;

    public ConvertiblePair(Class sourceType, Class dstType) {
        if (sourceType == null) {
            throw new IllegalArgumentException("sourceType can not be Null.");
        }
        if (dstType == null) {
            throw new IllegalArgumentException("dstType can not be Null.");
        }
        this.sourceType = sourceType;
        this.dstType = dstType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConvertiblePair that = (ConvertiblePair) o;

        if (!sourceType.equals(that.sourceType)) {
            return false;
        }
        return dstType.equals(that.dstType);
    }

    @Override
    public int hashCode() {
        int result = sourceType.hashCode();
        result = 31 * result + dstType.hashCode();
        return result;
    }
}


public static final class ConverterForPair {

    private GenericConverter genericConverter;

    public ConverterForPair(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }

    public GenericConverter getGenericConverter() {
        return genericConverter;
    }

    public void setGenericConverter(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }
}


private static class ConverterAdapter implements GenericConverter {
    private Converter converter;

    private ConvertiblePair convertiblePair;

    public ConverterAdapter(Converter converter, Class sourceType, Class dstType) {
        this.converter = converter;
        this.convertiblePair = new ConvertiblePair(sourceType, dstType);
    }

    @Override
    public Object convert(Object source, Class sourceType, Class dstType) {
        return this.converter.convert(source);
    }

    ;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConverterAdapter that = (ConverterAdapter) o;

        return convertiblePair.equals(that.convertiblePair);
    }

    @Override
    public int hashCode() {
        return convertiblePair.hashCode();
    }
}


private static class ConverterFactoryAdapter implements GenericConverter {

    private ConverterFactory converterFactory;

    private ConvertiblePair convertiblePair;

    public ConverterFactoryAdapter(ConverterFactory converterFactory, Class sourceType, Class dstType) {
        this.converterFactory = converterFactory;
        this.convertiblePair = new ConvertiblePair(sourceType, dstType);
    }

    @Override
    public Object convert(Object source, Class sourceType, Class dstType) {
        return this.converterFactory.getConverter(dstType).convert(source);
    }

    ;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConverterAdapter that = (ConverterAdapter) o;

        return convertiblePair.equals(that.convertiblePair);
    }

    @Override
    public int hashCode() {
        return convertiblePair.hashCode();
    }
}
}
