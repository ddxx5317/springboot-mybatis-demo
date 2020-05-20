package com.winter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {
    @GetMapping("/index")
    public String index(){
        String result = "SUCCESS";
        System.out.println(result);
        return result;
    }
}
