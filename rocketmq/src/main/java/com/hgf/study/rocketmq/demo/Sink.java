package com.hgf.study.rocketmq.demo;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author 黄耿锋
 * @date 2023/7/20 11:35
 **/
public interface Sink {

    String INPUT1 = "input1";

    @Input(INPUT1)
    SubscribableChannel input1();

}
