package com.hgf.tool.normal;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

class GraphicsCaptchaUtilTest {

    @Test
    void testCreateImage() throws IOException {
        //生成图形验证码
        Object[] objs = GraphicsCaptchaUtil.newBuilder()
                .setWidth(120)   //设置图片的宽度
                .setHeight(50)   //设置图片的高度
                .setSize(4)      //设置字符的个数
                .setLines(5)    //设置干扰线的条数
                .setFontSize(35) //设置字体的大小
                .setTilt(false)   //设置是否需要倾斜
                .setBackgroundColor(Color.LIGHT_GRAY) //设置验证码的背景颜色
                .build()         //构建VerifyUtil项目
                .createImage();  //生成图片
        System.out.println(objs[0]);
        System.out.println(objs[1]);
    }
}
