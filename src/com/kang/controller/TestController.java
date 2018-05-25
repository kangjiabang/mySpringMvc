package com.kang.controller;

import com.kang.annotation.*;
import com.kang.service.HelloWorldService;

/**
 * @Author：zeqi
 * @Date: Created in 09:38 23/5/18.
 * @Description:
 */
@Controller
public class TestController {


    @AutoWired
    private HelloWorldService helloWorldService;

    @RequestMapping("/test")
    public String  test(@RequestHeader(name="x-uid") int uid,@RequestParam(name="name") String name,@RequestParam(name="value",required = false,defaultValue = "test") String value) {

        return helloWorldService.helloWorld(name,value);
    }

}
