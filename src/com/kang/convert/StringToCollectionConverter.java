package com.kang.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：zeqi
 * @Date: Created in 17:05 25/5/18.
 * @Description:
 */


public class StringToCollectionConverter {

    private  ConvertService convertService;

    public StringToCollectionConverter(ConvertService convertService) {

        this.convertService = convertService;
    }

    public Collection convert(String source,Class sourceType,Class targetType,Class elementType) {

        String value = source.trim();
        if (value.equals("")) {
            return null;
        }
        if (!source.contains(",")) {
            throw new IllegalArgumentException("source '" + source + "' can not be converted to collection.");
        }
        String[] strs = source.split(",");

        Collection collection = createCollection(targetType);
        //创建集合
        createCollection(targetType);

        if (strs != null && strs.length > 0) {
            for (String str :strs) {

                Class dstType = elementType;
                Class srcElementType = str.getClass();
                if (srcElementType == dstType) {
                    collection.add(str);
                    continue;
                }
                ConvertService.ConverterForPair converterForPair = convertService.getConverterForPair(srcElementType, dstType);

                if (converterForPair != null) {
                    collection.add(converterForPair.getGenericConverter().convert(str, srcElementType, dstType));
                }
            }
        }
       return collection;
    }


    private Collection createCollection(Class targetType) {
        try {
            if (targetType == List.class)  {
                return new ArrayList();
            }
            return (Collection)targetType.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

