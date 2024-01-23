package com.hgf.study.easy_web.controller;

import cn.hutool.core.lang.Snowflake;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public void test(){
        Snowflake snowflake = new Snowflake(1L,1L);
        System.out.println(snowflake.nextId());
    }

}
