package com.kang.domain;

import java.lang.reflect.Method;

/**
 * @Author：zeqi
 * @Date: Created in 14:12 23/5/18.
 * @Description:
 */
public class HanderMethodParameter {

    private String name;

    private String defaultValue;

    private boolean required;

    public HanderMethodParameter(String name, String defaultValue, boolean required) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.required = required;
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

    public void setRequired(boolean required) {
        this.required = required;
    }
}
