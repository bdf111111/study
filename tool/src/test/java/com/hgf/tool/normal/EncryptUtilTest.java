package com.hgf.tool.normal;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

class EncryptUtilTest {

    @Test
    void testAesCbc() {
        // 加解密
        System.out.println(EncryptUtil.Aes.cbcEncrypt("hello world", "0123456789abcdef0123456789abcdef", "0123456789abcdef"));
        System.out.println(EncryptUtil.Aes.cbcDecrypt("k9UqsBbfHgv6fqa1leyUXw==", "0123456789abcdef0123456789abcdef", "0123456789abcdef"));
    }

}
