package com.hgf.tool.normal;


import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * RSA密钥工具类
 */
public class RSAKeyUtil {

    private RSAKeyUtil() {
    }

    /**
     * 生成密钥对
     *
     * @param keySize 密钥长度
     * @return 密钥对
     */
    public static RsaKeyPair generateKeyPair(KeySize keySize) throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(keySize.size, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        java.security.KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到公钥字符串
        String publicKeyString = encryptBASE64(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = encryptBASE64(privateKey.getEncoded());

        return new RsaKeyPair(publicKeyString, privateKeyString);
    }


    /**
     * 转换为公钥对象
     *
     * @param publicKeyText 公钥字符串
     * @return 公钥对象
     */
    public static PublicKey convertToPublicKey(String publicKeyText) throws NoSuchAlgorithmException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyText));
            return keyFactory.generatePublic(spec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException("無效的公鑰", e);
        }
    }


    /**
     * 转换为私钥对象
     *
     * @param privateKeyText 私钥字符串
     * @return 私钥对象
     */
    public static PrivateKey convertToPrivateKey(String privateKeyText) throws NoSuchAlgorithmException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyText));
            return keyFactory.generatePrivate(spec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException("無效的私鑰", e);
        }
    }


    /**
     * 编码返回字符串
     *
     * @param key 密钥
     * @return 编码后的字符串
     */
    private static String encryptBASE64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * 解码返回Byte数组
     *
     * @param key 密钥
     * @return 解码后的Byte数组
     */
    private static byte[] decryptBASE64(String key) {

        return (Base64.getDecoder().decode(key));
    }


    /**
     * 密钥对
     */
    public static class RsaKeyPair {

        public RsaKeyPair(String publicKeyString, String privateKeyString) {
            this.publicKeyString = publicKeyString;
            this.privateKeyString = privateKeyString;
        }

        /**
         * 公钥字符串
         */
        private final String publicKeyString;

        /**
         * 私钥字符串
         */
        private final String privateKeyString;


        public String getPublicKeyString() {
            return publicKeyString;
        }

        public String getPrivateKeyString() {
            return privateKeyString;
        }
    }

    /**
     * 密钥长度
     */
    public enum KeySize {

        /**
         * 2048
         */
        TOW_THOUSAND_FORTY_EIGHT(2048),

        /**
         * 4096
         */
        FOUR_THOUSAND_NINETY_SIX(4096);

        KeySize(int size) {
            this.size = size;
        }

        private final int size;

        public int getSize() {
            return size;
        }
    }
}