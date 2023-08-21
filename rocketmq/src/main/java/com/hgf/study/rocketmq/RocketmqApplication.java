package com.hgf.study.rocketmq;

import com.hgf.study.rocketmq.demo.Sink;
import com.hgf.study.rocketmq.demo.Source;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({Sink.class, Source.class})
@EnableDiscoveryClient
public class RocketmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication.class, args);
    }

}
