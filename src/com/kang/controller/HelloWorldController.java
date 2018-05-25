package com.kang.controller;

import com.kang.annotation.*;
import com.kang.service.HelloWorldService;

/**
 * @Author：zeqi
 * @Date: Created in 09:38 23/5/18.
 * @Description:
 */
@Controller
public class HelloWorldController {


    @AutoWired
    private HelloWorldService helloWorldService;

    @RequestMapping("/hello")
    public String  helloWorld(@RequestHeader(name="x-uid") int uid,@RequestParam(name="name") String name,@RequestParam(name="value",defaultValue = "世界") String value) {

        return helloWorldService.helloWorld(name,value) + " uid:" + uid;
    }

    @RequestMapping("/hello2")
    public String  helloWorld2(@RequestParam(name="name") String name,@RequestParam(name="value",defaultValue = "世界") String value) {

        return helloWorldService.helloWorld(name,value);
    }
}
