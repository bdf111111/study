package com.hgf.study.easy_web.controller;

import com.hgf.tool.normal.IPUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public void test(HttpServletRequest request){
        System.out.println(IPUtil.getIpAddress(request));
    }

}
