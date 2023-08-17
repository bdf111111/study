package com.hgf.study.aop_read_write_separate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hgf.study.aop_read_write_separate.mapper")
public class AopReadWriteSeparateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopReadWriteSeparateApplication.class, args);
    }

}
