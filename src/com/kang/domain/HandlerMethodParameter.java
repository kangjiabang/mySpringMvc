package com.kang.domain;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author：zeqi
 * @Date: Created in 14:12 23/5/18.
 * @Description:
 */
public class HandlerMethodParameter {

    private String name;

    private String defaultValue;

    private boolean required;

    private Parameter parameter;

    /**
     *  1-参数  2-消息头,com.kang.enums.ParamterType
     */
    private int type;

    public HandlerMethodParameter(Parameter parameter,String name, String defaultValue, boolean required, int type) {
        this.parameter = parameter;
        this.name = name;
        this.defaultValue = defaultValue;
        this.required = required;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
