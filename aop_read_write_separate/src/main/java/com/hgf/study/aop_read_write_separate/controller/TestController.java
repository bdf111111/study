package com.hgf.study.aop_read_write_separate.controller;

import com.hgf.study.aop_read_write_separate.config.A;
import com.hgf.study.aop_read_write_separate.entity.Test;
import com.hgf.study.aop_read_write_separate.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @GetMapping("/testa")
    public void a(){
        Test test = testMapper.selectById(1);
        System.out.println(test);
    }

    @GetMapping("/testb")
    public void b(){
        Test test = testMapper.selectById(2);
        System.out.println(test);
    }

}
