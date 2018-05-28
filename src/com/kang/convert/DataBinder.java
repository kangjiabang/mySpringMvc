package com.kang.convert;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @Author：zeqi
 * @Date: Created in 16:39 24/5/18.
 * @Description:
 */
public class DataBinder {

    private ConvertService convertService;

    public DataBinder() {
        this.convertService = new ConvertService();
    }

    public Object convertIfNecessary(Object arg, Parameter parameter) {

        if (arg.getClass() == String.class && Collection.class.isAssignableFrom(parameter.getType())) {
            Type type = parameter.getParameterizedType();
            ParameterizedType pt = (ParameterizedType) type;
            Class elementType = (Class) pt.getActualTypeArguments()[0];
            return new StringToCollectionConverter(convertService).convert((String)arg,arg.getClass(),parameter.getType(),elementType);
        }
        return convertService.convert(arg, parameter);

    }


}
