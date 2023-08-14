package com.hgf.study.rocketmq.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 黄耿锋
 * @date 2023/7/20 11:25
 **/
@Service
public class Producer implements CommandLineRunner {

    @Resource
    private MessageChannel output1; // 获取name为output的binding

    public void produce(){
        output1.send(MessageBuilder.withPayload("test1").setHeader("test", "1").build());
    }

    @Override
    public void run(String... args) throws Exception {
        produce();
    }

}
