package com.hgf.study.rocketmq.demo;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @author 黄耿锋
 * @date 2023/7/20 11:46
 **/
@Service
public class Consumer {

    @StreamListener(Sink.INPUT1)
    public void consume(String receiveMsg){
        System.out.println(receiveMsg);
    }

}
