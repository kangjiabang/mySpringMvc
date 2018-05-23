package com.kang.domain;

import java.lang.reflect.Method;

/**
 * @Author：zeqi
 * @Date: Created in 14:12 23/5/18.
 * @Description:
 */
public class HanderMethod {

    private Method method;

    private Object bean;

    public HanderMethod(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
    }


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
