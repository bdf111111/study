package com.hgf.tool.normal;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class QRCodeUtilTest {

    @Test
    void testGenerate() {
        //二维码
        System.out.println("data:image/jpg;base64," + Base64.getEncoder().encodeToString(QRCodeUtil.generate("https://www.baidu.com", 200, 200, "jpg")));
    }

}
