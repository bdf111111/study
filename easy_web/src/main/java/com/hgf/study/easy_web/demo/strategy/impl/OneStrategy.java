package com.hgf.study.easy_web.demo.strategy.impl;

import com.hgf.study.easy_web.demo.strategy.NumberStrategy;
import org.springframework.stereotype.Component;

/**
 * @author huanggf
 * @date 2025/7/22
 */
@Component
public class OneStrategy implements NumberStrategy {

    @Override
    public Integer code() {
        return 1;
    }

    @Override
    public void handle() {
        System.out.println(1);
    }

}
