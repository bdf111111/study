package com.hgf.tool.normal;

import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientUtilTest {

    @Test
    void testGetAndPost() {
        //http
        System.out.println(HttpClientUtil.doGet("https://www.baidu.com", true));
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("x-client-id", "JKuVj9bzS8S5gI7rxaE2Sw");
        headerMap.put("x-api-key", "9ed805c306ec05db85766c0a6df44c8e9dfa1e1071e2a8613971c8f18cc95611c65fb75d144375f70b104eb34406a7d4");
        System.out.println(HttpClientUtil.doHttpPost("https://api-demo.airwallex.com/api/v1/authentication/login", "", headerMap, false));
    }

}
