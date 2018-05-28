package com.kang.controller;

import com.kang.annotation.*;
import com.kang.service.HelloWorldService;

import java.util.List;

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
    public String  helloWorld(@RequestHeader(name="x-uid") Long uid,@RequestParam(name="name") String name,@RequestParam(name="value",defaultValue = "世界") String value) {

        return helloWorldService.helloWorld(name,value) + " uid:" + uid;
    }

    @RequestMapping("/helloBoolean")
    public String  helloWorld(@RequestHeader(name="isOn") Boolean isOn) {

        return  " isOn:" + isOn;
    }

    @RequestMapping("/helloNames")
    public String  helloWorld(@RequestHeader(name="names") List<Integer> names) {

        return  " names:" + names.toString();
    }

    @RequestMapping("/hello2")
    public String  helloWorld2(@RequestParam(name="name") String name,@RequestParam(name="value",defaultValue = "世界") String value) {

        return helloWorldService.helloWorld(name,value);
    }
}
