package com.hgf.tool.wechat;

public enum WeChatUrlEnum {

    JS_CODE_2_SESSION("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"),
    GET_ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s"),
    GET_MIN_APP_CODE("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s"),
    GET_PHONE_NUMBER("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s");

    private String url;

    WeChatUrlEnum() {
    }

    WeChatUrlEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public WeChatUrlEnum setUrl(String url) {
        this.url = url;
        return this;
    }
}
