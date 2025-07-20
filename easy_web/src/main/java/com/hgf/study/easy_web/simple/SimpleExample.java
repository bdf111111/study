package com.hgf.study.easy_web.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgf.tool.json.JsonUtil;
import com.hgf.tool.normal.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * @author huanggf
 * @date 2024/12/2
 */
public class SimpleExample {


    public static void main(String[] args) throws IOException {
        // 工作日
//        System.out.println(BusinessDayUtil.getNextBusinessDay(ZonedDateTime.now().plusDays(3))
//                .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
        // 加解密
//        System.out.println(EncryptUtil.Aes.cbcEncrypt("hello world", "0123456789abcdef0123456789abcdef", "0123456789abcdef"));
//        System.out.println(EncryptUtil.Aes.cbcDecrypt("k9UqsBbfHgv6fqa1leyUXw==", "0123456789abcdef0123456789abcdef", "0123456789abcdef"));
//
        //二维码
//        System.out.println(Base64.getEncoder().encodeToString(QRCodeUtil.generate("https://www.baidu.com", 200, 200, "jpg")));

        //http
//        System.out.println(HttpClientUtil.doGet("https://www.12306.cn/mormhweb", true));
//        Map<String, String> headerMap = new HashMap<>();
//        headerMap.put("Content-Type", "application/json");
//        headerMap.put("x-client-id", "JKuVj9bzS8S5gI7rxaE2Sw");
//        headerMap.put("x-api-key", "9ed805c306ec05db85766c0a6df44c8e9dfa1e1071e2a8613971c8f18cc95611c65fb75d144375f70b104eb34406a7d4");
//        System.out.println(HttpClientUtil.doHttpPost("https://api-demo.airwallex.com/api/v1/authentication/login", "", headerMap, false));

        //金额格式化
//        System.out.println(AmountUtil.amountFormat(new BigDecimal("1000000.11"), "JPY"));

        //生成图形验证码
//        Object[] objs = VerifyUtil.newBuilder()
//                .setWidth(120)   //设置图片的宽度
//                .setHeight(50)   //设置图片的高度
//                .setSize(4)      //设置字符的个数
//                .setLines(5)    //设置干扰线的条数
//                .setFontSize(35) //设置字体的大小
//                .setTilt(false)   //设置是否需要倾斜
//                .setBackgroundColor(Color.LIGHT_GRAY) //设置验证码的背景颜色
//                .build()         //构建VerifyUtil项目
//                .createImage();  //生成图片
//        System.out.println(objs[0]);
//        System.out.println(objs[1]);

        // 正则
//        System.out.println(PatternUtil.isNumberString("1.1"));
//        System.out.println(PatternUtil.isEmail("1.1"));

        // 唯一id
//        Snowflake snowflake = new Snowflake(1L,1L);
//        System.out.println(snowflake.nextId());

        // 对象转map/驼峰转下划线
        ClassOne classOne = new ClassOne();
        classOne.setName("classOne");
        List<ClassTwo> classTwoList = new ArrayList<>();
        ClassTwo classTwo1 = new ClassTwo();
        classTwo1.setName("classTwo1");
        classTwoList.add(classTwo1);
        ClassTwo classTwo2 = new ClassTwo();
        classTwo2.setName("classTwo2");
        classTwoList.add(classTwo2);
        classOne.setClassTwos(classTwoList);
        ObjectMapper objectMapper =new ObjectMapper();
        Map<String, Object> map =  objectMapper.convertValue(classOne, Map.class);
        System.out.println(map);
        System.out.println(ObjectMapperUtil.toUnderlineJSONString(classOne));

        // builder
//        System.out.println(JsonUtil.toJsonString(BuilderUtil.of(ClassOne::new)
//                .with(ClassOne::setName, "classOne").builder()));
    }

}
