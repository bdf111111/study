package com.hgf.study.shardings_phere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hgf.study.shardings_phere.mapper")
public class ShardingsPhereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsPhereApplication.class, args);
    }

}
