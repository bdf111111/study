package com.hgf.study.easy_web.controller;

import com.hgf.study.easy_web.demo.strategy.NumberStrategyFactory;
import com.hgf.tool.normal.IPUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    NumberStrategyFactory numberStrategyFactory;

    @GetMapping("/test")
    public void test(HttpServletRequest request){
        System.out.println(IPUtil.getIpAddress(request));
    }

    @GetMapping("/strategy")
    public void test(Integer strategyCode){
        numberStrategyFactory.getStrategy(strategyCode).handle();
    }

}
