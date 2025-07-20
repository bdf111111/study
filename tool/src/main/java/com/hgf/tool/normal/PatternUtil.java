package com.hgf.tool.normal;

import com.hgf.tool.common.model.constant.RegexConstant;

import java.util.regex.Pattern;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class PatternUtil {

    /**
     * 是否為純數字字符串
     *
     * @param value 字符串內容
     * @return 返回校驗結果
     */
    public static Boolean isNumberString(String value) {
        return doVerify(RegexConstant.NUMERICAL_REG, value);
    }

    /**
     * 是否為數字字符串
     *
     * @param value 字符串內容
     * @return 返回校驗結果
     */
    public static Boolean isDecimalNumberString(String value) {
        return doVerify(RegexConstant.PURE_NUMBER_REG, value);
    }

    /**
     * 校验手机号
     *
     * @param mobile 手机号
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return doVerify(RegexConstant.CN_PHONE_NUMBER_REG, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return doVerify(RegexConstant.EMAIL_REG, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese 中文
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return doVerify(RegexConstant.CHINESE_REG, chinese);
    }

    /**
     * 正则校验
     *
     * @param regularExpression 正则表达式
     * @param data              待检查数据
     * @return 校验通过返回true，否则返回false
     */
    public static boolean doVerify(String regularExpression, String data) {
        if (StringUtil.isEmpty(regularExpression) || StringUtil.isEmpty(data)) {
            return false;
        }
        return Pattern.matches(regularExpression, data);
    }

}

