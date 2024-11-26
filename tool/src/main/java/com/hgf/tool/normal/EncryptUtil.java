package com.hgf.tool.normal;

import com.hgf.tool.model.exception.EncryptException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 加密解密工具类
 */
public class EncryptUtil {

    private EncryptUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * HmacSHA1加密
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 加密数据
     */
    public static String sha1(String data, String key) {
        try {
            byte[] keyBytes = key.getBytes();
            // 根据给定的字节数组构造一个密钥。
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(data.getBytes());

            return byte2hex(rawHmac);
        } catch (Exception exception) {
            log.error("HmacSHA1加密异常: {}", exception.getMessage(), exception);
            return null;
        }
    }


    /**
     * HmacSHA256加密
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 加密数据
     */
    public static String sha256(String data, String key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data.getBytes()));
        } catch (Exception exception) {
            log.error("HmacSHA256加密异常: {}", exception.getMessage(), exception);
            return null;
        }
    }

    /**
     * HmacSHA512加密
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 加密数据
     */
    public static String sha512(String data, String key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data.getBytes()));
        } catch (Exception exception) {
            log.error("HmacSHA512加密异常: {}", exception.getMessage(), exception);
            return null;
        }
    }

    /**
     * SHA256withRSA签名
     *
     * @param privateKeyText 私钥字符串
     * @param data           明文
     * @return 签名
     */
    public static String sha256WithRsaSign(String privateKeyText, String data) {
        PrivateKey privateKey;
        try {
            privateKey = RSAKeyUtil.convertToPrivateKey(privateKeyText);
        } catch (Exception exception) {
            log.error("转换私钥失败: {}", exception.getMessage(), exception);
            return null;
        }
        return sha256WithRsaSign(privateKey, data);
    }

    /**
     * SHA256withRSA签名
     *
     * @param privateKey 私钥
     * @param data       明文
     * @return 签名
     */
    public static String sha256WithRsaSign(PrivateKey privateKey, String data) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signed = sign.sign();
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception exception) {
            log.error("SHA256withRSA签名异常: {}", exception.getMessage(), exception);
            return null;
        }
    }

    /**
     * SHA256withRSA验签
     *
     * @param publicKeyText 公钥字符串
     * @param plainText     明文
     * @param signature     签名
     * @return 是否成功
     */
    public static boolean sha256WithRsaSignVerify(String publicKeyText, String plainText, String signature) {
        PublicKey publicKey;
        try {
            publicKey = RSAKeyUtil.convertToPublicKey(publicKeyText);
        } catch (Exception exception) {
            log.error("转换公钥失败: {}", exception.getMessage(), exception);
            return false;
        }
        return sha256WithRsaSignVerify(publicKey, plainText, signature);
    }

    /**
     * SHA256withRSA验签
     *
     * @param publicKey 公钥
     * @param plainText 明文
     * @param signature 签名
     * @return 是否成功
     */
    public static boolean sha256WithRsaSignVerify(PublicKey publicKey, String plainText, String signature) {
        try {
            Signature verifySign = Signature.getInstance("SHA256withRSA");
            verifySign.initVerify(publicKey);
            verifySign.update(plainText.getBytes(StandardCharsets.UTF_8));
            return verifySign.verify(Base64.getDecoder().decode(signature));
        } catch (Exception exception) {
            log.error("SHA256withRSA验签异常: {}", exception.getMessage(), exception);
            return false;
        }
    }


    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String temp = null;
        for (int n = 0; b != null && n < b.length; n++) {
            temp = Integer.toHexString(b[n] & 0XFF);
            if (temp.length() == 1) {
                hs.append('0');
            }
            hs.append(temp);
        }
        return hs.toString().toUpperCase();
    }


    public static byte[] hex2byte(String str) {
        str = str.toUpperCase();
        int length = (str.length() + 1) / 2;
        byte[] b = new byte[length];
        if (str.length() % 2 == 1) {
            str = str + "0";
        }
        for (int i = 0; i < str.length() / 2; i++) {
            int j = i * 2;
            byte x = str.substring(j, j + 1).getBytes()[0];
            byte y = str.substring(j + 1, j + 2).getBytes()[0];
            if (x > 57) {
                b[i] = (byte) (x - 'A' + 10);
            } else {
                b[i] = (byte) (x - '0');
            }
            b[i] = (byte) ((int) b[i] << 4);
            byte tmp;
            if (y > 57) {
                tmp = (byte) (y - 'A' + 10);
            } else {
                tmp = (byte) (y - '0');
            }
            b[i] += tmp;
        }

        return b;
    }

    /**
     * OAEPWithSHA加密
     *
     * @param plainText     明⽂待加密字符串
     * @param publicKeyText 公鑰證書字符串
     * @return 返回加密後的密⽂
     */
    public static String encryptByRsaOAEP(String plainText, String publicKeyText) throws NoSuchAlgorithmException, IllegalArgumentException, IllegalBlockSizeException {

        PublicKey publicKey = RSAKeyUtil.convertToPublicKey(publicKeyText);

        return encryptByRsaOAEP(plainText, publicKey);
    }

    /**
     * OAEPWithSHA加密
     *
     * @param plainText 明⽂待加密字串
     * @param publicKey 公鑰證書
     * @return 返回加密後的密⽂
     */
    public static String encryptByRsaOAEP(String plainText, PublicKey publicKey) throws IllegalArgumentException, IllegalBlockSizeException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
            byte[] cipherTextBytes = cipher.doFinal(plainTextBytes);
            return Base64.getEncoder().encodeToString(cipherTextBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException exception) {
            throw new EncryptException("當前Java 環境不⽀持RSA v1.5/OAEP", exception);
        } catch (InvalidKeyException exception) {
            throw new IllegalArgumentException("無效的證書", exception);
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            throw new IllegalBlockSizeException("加密原字串的⻑度不能超過214 字節");
        }
    }

    /**
     * OAEPWithSHA加密(分段)
     *
     * @param plainText     明⽂待加密字符串
     * @param publicKeyText 公鑰證書字符串
     * @return 返回加密後的密⽂
     */
    public static String segmentEncryptByRsaOAEP(String plainText, String publicKeyText) throws NoSuchAlgorithmException, IllegalArgumentException, IllegalBlockSizeException {

        PublicKey publicKey = RSAKeyUtil.convertToPublicKey(publicKeyText);

        return segmentEncryptByRsaOAEP(plainText, publicKey);
    }

    /**
     * OAEPWithSHA加密(分段)
     *
     * @param plainText 明⽂待加密字串
     * @param publicKey 公鑰證書
     * @return 返回加密後的密⽂
     */
    public static String segmentEncryptByRsaOAEP(String plainText, PublicKey publicKey) throws IllegalArgumentException, IllegalBlockSizeException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
            int inputLen = plainTextBytes.length;

            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 214) {
                    cache = cipher.doFinal(plainTextBytes, offSet, 214);
                } else {
                    cache = cipher.doFinal(plainTextBytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 214;
            }
            byte[] encryptedData = out.toByteArray();

            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException exception) {
            throw new EncryptException("當前Java 環境不⽀持RSA v1.5/OAEP", exception);
        } catch (InvalidKeyException exception) {
            throw new IllegalArgumentException("無效的證書", exception);
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            throw new IllegalBlockSizeException("加密原字串的⻑度不能超過214 字節");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * OAEPWithSHA解密
     *
     * @param cipherText     密⽂待解密字符串
     * @param privateKeyText 私鑰證書字符串
     * @return 返回解密後的明⽂
     */
    public static String decryptByRsaOAEP(String cipherText, String privateKeyText) throws NoSuchAlgorithmException, BadPaddingException, IllegalArgumentException {

        PrivateKey privateKey = RSAKeyUtil.convertToPrivateKey(privateKeyText);

        return decryptByRsaOAEP(cipherText, privateKey);
    }

    /**
     * OAEPWithSHA解密
     *
     * @param cipherText 密⽂待解密字串
     * @param privateKey 私鑰證書
     * @return 返回解密後的明⽂
     */
    public static String decryptByRsaOAEP(String cipherText, PrivateKey privateKey) throws BadPaddingException, IllegalArgumentException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
            return new String(cipher.doFinal(cipherTextBytes), StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException exception) {
            throw new EncryptException("當前Java 環境不⽀持RSA v1.5/OAEP", exception);
        } catch (InvalidKeyException exception) {
            throw new IllegalArgumentException("無效的私鑰", exception);
        } catch (BadPaddingException | IllegalBlockSizeException exception) {
            throw new BadPaddingException("解密失敗");
        }
    }

    /**
     * OAEPWithSHA解密(分段)
     *
     * @param cipherText     密⽂待解密字符串
     * @param privateKeyText 私鑰證書字符串
     * @return 返回解密後的明⽂
     */
    public static String segmentDecryptByRsaOAEP(String cipherText, String privateKeyText) throws NoSuchAlgorithmException, BadPaddingException, IllegalArgumentException {

        PrivateKey privateKey = RSAKeyUtil.convertToPrivateKey(privateKeyText);

        return segmentDecryptByRsaOAEP(cipherText, privateKey);
    }

    /**
     * OAEPWithSHA解密(分段)
     *
     * @param cipherText 密⽂待解密字串
     * @param privateKey 私鑰證書
     * @return 返回解密後的明⽂
     */
    public static String segmentDecryptByRsaOAEP(String cipherText, PrivateKey privateKey) throws BadPaddingException, IllegalArgumentException {

        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
            int inputLen = cipherTextBytes.length;

            int offset = 0;
            byte[] cache;
            int i = 0;

            // 对数据分段解密
            while (inputLen - offset > 0) {
                if (inputLen - offset > 256) {
                    cache = cipher.doFinal(cipherTextBytes, offset, 256);
                } else {
                    cache = cipher.doFinal(cipherTextBytes, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * 256;
            }
            byte[] decryptedData = out.toByteArray();

            return new String(decryptedData, StandardCharsets.UTF_8);

        } catch (NoSuchPaddingException | NoSuchAlgorithmException exception) {
            throw new EncryptException("當前Java 環境不⽀持RSA v1.5/OAEP", exception);
        } catch (InvalidKeyException exception) {
            throw new IllegalArgumentException("無效的私鑰", exception);
        } catch (BadPaddingException | IllegalBlockSizeException exception) {
            throw new BadPaddingException("解密失敗");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static class Des {

        private Des() {
        }

        private static final String SECRET_KET_TYPE = "DES";
        private static final String ENCODE = "GBK";

        /**
         * 根据键值进行加密
         *
         * @param data 数据内容
         * @param key  加密Key
         * @return 返回加密后的密文
         * @throws InvalidKeyException       无效Key一次样
         * @throws NoSuchAlgorithmException  没有找到算法异常
         * @throws InvalidKeySpecException   无效key算法异常
         * @throws NoSuchPaddingException    没有匹配的算法异常
         * @throws IllegalBlockSizeException 无效长度异常
         * @throws BadPaddingException       错误的算法异常
         * @throws IOException               IO异常
         */
        public static String encrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
            return byte2hex(encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE)));
        }

        /**
         * 根据键值进行解密
         *
         * @param data 数据内容
         * @param key  加密Key
         * @return 返回解密后的明文
         * @throws InvalidKeyException       无效Key一次样
         * @throws NoSuchAlgorithmException  没有找到算法异常
         * @throws InvalidKeySpecException   无效key算法异常
         * @throws NoSuchPaddingException    没有匹配的算法异常
         * @throws IllegalBlockSizeException 无效长度异常
         * @throws BadPaddingException       错误的算法异常
         * @throws IOException               IO异常
         */
        public static String decrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
            if (data == null) {
                return null;
            }
            return new String(decrypt(hex2byte(data), key.getBytes(ENCODE)), ENCODE);
        }

        /**
         * 根据键值进行加密
         *
         * @param data 数据内容
         * @param key  加密Key
         * @return 返回加密后的密文
         * @throws InvalidKeyException       无效Key一次样
         * @throws NoSuchAlgorithmException  没有找到算法异常
         * @throws InvalidKeySpecException   无效key算法异常
         * @throws NoSuchPaddingException    没有匹配的算法异常
         * @throws IllegalBlockSizeException 无效长度异常
         * @throws BadPaddingException       错误的算法异常
         */
        private static byte[] encrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KET_TYPE);
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(SECRET_KET_TYPE);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

            return cipher.doFinal(data);
        }

        /**
         * 根据键值进行解密
         *
         * @param data 数据内容
         * @param key  加密Key
         * @return 返回解密后的明文
         * @throws InvalidKeyException       无效Key一次样
         * @throws NoSuchAlgorithmException  没有找到算法异常
         * @throws InvalidKeySpecException   无效key算法异常
         * @throws NoSuchPaddingException    没有匹配的算法异常
         * @throws IllegalBlockSizeException 无效长度异常
         * @throws BadPaddingException       错误的算法异常
         */
        private static byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KET_TYPE);
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(SECRET_KET_TYPE);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

            return cipher.doFinal(data);
        }
    }

    public static class Aes {

        private Aes() {
        }

        private static final String SECRET_KET_TYPE = "AES";
        private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS7Padding";
        private static final String CIPHER_ALGORITHM_CFB = "AES/CFB/PKCS7Padding";

        static {
            Security.addProvider(new BouncyCastleProvider());
        }

        /**
         * AES加密CBC模式PKCS7Padding填充方式
         *
         * @param data 加密数据
         * @param key  密钥
         * @param iv   初始化向量
         * @return 加密字符串
         */
        public static String cbcEncrypt(String data, String key, String iv) {
            return encrypt(CIPHER_ALGORITHM_CBC, data, key, iv);
        }

        /**
         * AES解密CBC模式PKCS7Padding填充方式
         *
         * @param data 加密数据
         * @param key  密钥
         * @param iv   初始化向量
         * @return 解密字符串
         */
        public static String cbcDecrypt(String data, String key, String iv) {
            return decrypt(CIPHER_ALGORITHM_CBC, data, key, iv);
        }

        /**
         * AES加密CFB模式PKCS7Padding填充方式
         *
         * @param data 加密数据
         * @param key  密钥
         * @param iv   初始化向量
         * @return 加密字符串
         */
        public static String cfbEncrypt(String data, String key, String iv) {
            return encrypt(CIPHER_ALGORITHM_CFB, data, key, iv);
        }

        /**
         * AES解密CFB模式PKCS7Padding填充方式
         *
         * @param data 加密数据
         * @param key  密钥
         * @param iv   初始化向量
         * @return 解密字符串
         */
        public static String cfbDecrypt(String data, String key, String iv) {
            return decrypt(CIPHER_ALGORITHM_CFB, data, key, iv);
        }


        /**
         * AES加密
         *
         * @param data 加密数据
         * @param key  密钥
         * @param iv   初始化向量
         * @return 加密字符串
         */
        private static String encrypt(String cipherAlgorithm, String data, String key, String iv) {
            try {
                Cipher cipher = Cipher.getInstance(cipherAlgorithm);

                byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, SECRET_KET_TYPE);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(iv.getBytes()));

                byte[] result = cipher.doFinal(data.getBytes());
                return new String(Base64.getEncoder().encode(result));
            } catch (Exception exception) {
                log.error("AES256加密异常: {}", exception.getMessage(), exception);
                return null;
            }
        }

        /**
         * AES解密
         *
         * @param data 解密数据
         * @param key  密钥
         * @param iv   初始化向量
         * @return 解密字符串
         */
        private static String decrypt(String cipherAlgorithm, String data, String key, String iv) {
            try {
                Cipher cipher = Cipher.getInstance(cipherAlgorithm);

                byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, SECRET_KET_TYPE);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv.getBytes()));

                byte[] result = cipher.doFinal(Base64.getDecoder().decode(data));
                return new String(result);
            } catch (Exception exception) {
                log.error("AES256解密异常: {}", exception.getMessage(), exception);
                return null;
            }
        }
    }

}