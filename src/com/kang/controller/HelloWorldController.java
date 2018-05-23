package com.kang.controller;

import com.kang.annotation.Controller;
import com.kang.annotation.RequestMapping;
import com.kang.annotation.RequestParam;

/**
 * @Author：zeqi
 * @Date: Created in 09:38 23/5/18.
 * @Description:
 */
@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    public String  helloWorld(@RequestParam(name="name") String name,@RequestParam(name="value",defaultValue = "世界") String value) {
        return name + " " + value;
    }
}
