package com.hgf.study.rocketmq.demo;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author 黄耿锋
 * @date 2023/7/20 11:43
 **/
public interface Source {

    String OUTPUT1 = "output1";

    @Output(OUTPUT1)
    SubscribableChannel output1();

}
