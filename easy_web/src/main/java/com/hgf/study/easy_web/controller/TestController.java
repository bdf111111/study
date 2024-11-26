package com.hgf.study.easy_web.controller;

import cn.hutool.core.lang.Snowflake;
import com.hgf.tool.normal.BusinessDayUtil;
import com.hgf.tool.normal.EncryptUtil;
import com.hgf.tool.normal.QRCodeUtil;
import com.hgf.tool.normal.VersionUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("/test")
public class TestController {

    public static void main(String[] args) {
//        // 工作日
//        System.out.println(BusinessDayUtil.getNextBusinessDay(ZonedDateTime.now().plusDays(3))
//                .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
//        // 加解密
//        System.out.println(EncryptUtil.Aes.cbcEncrypt("hello world", "0123456789abcdef0123456789abcdef", "0123456789abcdef"));
//        System.out.println(EncryptUtil.Aes.cbcDecrypt("k9UqsBbfHgv6fqa1leyUXw==", "0123456789abcdef0123456789abcdef", "0123456789abcdef"));

        //二维码
        System.out.println(Base64.getEncoder().encodeToString(QRCodeUtil.generate("https://www.baidu.com", 200, 200, "jpg")));
    }

    @GetMapping("/test")
    public void test(){
        Snowflake snowflake = new Snowflake(1L,1L);
        System.out.println(snowflake.nextId());
    }

}
