package com.kang.service;

import com.kang.annotation.Component;

/**
 * @Author：zeqi
 * @Date: Created in 17:20 23/5/18.
 * @Description:
 */
@Component
public class HelloWorldService {

    /**
     *
     * @param name
     * @param value
     * @return
     */
    public String helloWorld(String name,String value) {
        return name + " " + value;
    }
}
