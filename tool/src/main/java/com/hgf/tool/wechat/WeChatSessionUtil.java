package com.hgf.tool.wechat;

import com.alibaba.fastjson.JSONObject;
import com.hgf.tool.normal.HttpClientUtil;
import com.hgf.tool.normal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WeChatSessionUtil {
    private static final Logger LOG = LoggerFactory.getLogger(WeChatSessionUtil.class);
    // 获取session_key
    private static final String OPEN_ID = "openid";
    private static final String SESSION_KEY = "session_key";
    // 解密
    private static final String AES = "AES";
    private static final String CIPHER_CODE = "AES/CBC/PKCS5Padding";
    // access_token信息
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    // 手机号相关
    private static final String PHONE_INFO = "phone_info";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String PURE_PHONE_NUMBER = "purePhoneNumber";
    private static final String COUNTRY_CODE = "countryCode";

    /**
     * 请求微信后台获取用户数据
     *
     * @param appid 小程序id
     * @param secret 小程序密钥
     * @param code wx.login获取到的临时code
     * @return 用户数据
     */
    public static WeChatSessionInfo sessionInfo(String appid, String secret, String code) {
        //创建请求通过code换取session等数据
        String url = String.format(WeChatUrlEnum.JS_CODE_2_SESSION.getUrl(), appid, secret, code);
        String sessionInfoStr = HttpClientUtil.doGet(url);
        if (StringUtil.isNotEmpty(sessionInfoStr)) {
            LOG.debug("WeChatSessionUtil sessionInfo :{}", sessionInfoStr);
            JSONObject jsonObject = JSONObject.parseObject(sessionInfoStr);
            return new WeChatSessionInfo(jsonObject.getString(OPEN_ID), jsonObject.getString(SESSION_KEY));
        }
        return null;
    }

    /**
     * 解密用户敏感数据
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return 用户敏感数据
     */
    public static WeChatAccessPhoneInfo decryptData(String encryptedData, String sessionKey, String iv) {
        WeChatAccessPhoneInfo accessPhoneInfo = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(sessionKey), AES);
            Cipher cipher = Cipher.getInstance(CIPHER_CODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(Base64.getDecoder().decode(iv)));
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            String dataStr = new String(decrypted, StandardCharsets.UTF_8);
            if (StringUtil.isNotEmpty(dataStr)) {
                JSONObject object = JSONObject.parseObject(dataStr);
                accessPhoneInfo = new WeChatAccessPhoneInfo(object.getString(PHONE_NUMBER),
                        object.getString(PURE_PHONE_NUMBER),object.getString(COUNTRY_CODE));
            }
        } catch (Exception e) {
            LOG.error("decryptData exception:", e);
        }
        return accessPhoneInfo;
    }


    /**
     * 获取微信接口调用凭证
     *
     * @param appid 小程序id
     * @param secret 小程序密钥
     * @return 微信接口调用凭证
     */
    public static WeChatAccessToken getAccessToken(String appid, String secret) {
        String tokenStr = HttpClientUtil.doGet(String.format(WeChatUrlEnum.GET_ACCESS_TOKEN.getUrl(), appid, secret));
        JSONObject jsonObject = JSONObject.parseObject(tokenStr);
        return new WeChatAccessToken(jsonObject.getString(ACCESS_TOKEN), jsonObject.getInteger(EXPIRES_IN));
    }


    /**
     * 获取小程序码byte array
     *
     * @param token 微信接口调用凭证
     * @param jsonParameter 参数
     * @return 小程序码byte array
     */
    public static byte[] getMinAppCodeByteArray(String token, String jsonParameter) {
        return HttpClientUtil.responseBinaryPost(String.format(WeChatUrlEnum.GET_MIN_APP_CODE.getUrl(), token), jsonParameter);
    }


    /**
     * 获取手机号码
     * @param accessToken 微信接口调用凭证
     * @param code 小程序手机号授权回调的code
     * @return 手机号码
     */
    public static String getPhoneNumber(String accessToken, String code) {
        String url = String.format(WeChatUrlEnum.GET_PHONE_NUMBER.getUrl(), accessToken);
        String json = "{\"code\":\""+ StringUtil.nullToEmpty(code) +"\"}";
        String postResult = HttpClientUtil.doPost(url, json, false);
        JSONObject jsonObject = JSONObject.parseObject(postResult);
        JSONObject phoneInfo = jsonObject.getJSONObject(PHONE_INFO);
        if(phoneInfo == null){
            LOG.error("获取手机号码失败");
        }
        return StringUtil.nullToEmpty(phoneInfo.getString(PHONE_NUMBER));
    }

    public static class WeChatSessionInfo implements Serializable{
        private String openId;
        private String sessionKey;

        public WeChatSessionInfo() {
        }

        public WeChatSessionInfo(String openId, String sessionKey) {
            this.openId = openId;
            this.sessionKey = sessionKey;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getSessionKey() {
            return sessionKey;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }

        @Override
        public String toString() {
            return "WeChatSessionInfo{" +
                    "openId='" + openId + '\'' +
                    ", sessionKey='" + sessionKey + '\'' +
                    '}';
        }
    }

    public static class WeChatAccessToken implements Serializable {
        private String accessToken;
        private Integer expires;

        public WeChatAccessToken() {
        }

        public WeChatAccessToken(String accessToken, Integer expires) {
            this.accessToken = accessToken;
            this.expires = expires;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Integer getExpires() {
            return expires;
        }

        public void setExpires(Integer expires) {
            this.expires = expires;
        }

        @Override
        public String toString() {
            return "WeChatAccessToken{" +
                    "accessToken='" + accessToken + '\'' +
                    ", expires=" + expires +
                    '}';
        }
    }

    public static class WeChatAccessPhoneInfo implements Serializable{
        private String phoneNumber;
        private String purePhoneNumber;
        private String countryCode;

        public WeChatAccessPhoneInfo() {
        }

        public WeChatAccessPhoneInfo(String phoneNumber, String purePhoneNumber, String countryCode) {
            this.phoneNumber = phoneNumber;
            this.purePhoneNumber = purePhoneNumber;
            this.countryCode = countryCode;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPurePhoneNumber() {
            return purePhoneNumber;
        }

        public void setPurePhoneNumber(String purePhoneNumber) {
            this.purePhoneNumber = purePhoneNumber;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        @Override
        public String toString() {
            return "WeChatAccessPhoneInfo{" +
                    "phoneNumber='" + phoneNumber + '\'' +
                    ", purePhoneNumber='" + purePhoneNumber + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    '}';
        }
    }


}
