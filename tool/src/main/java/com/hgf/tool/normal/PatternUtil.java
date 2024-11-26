package com.hgf.tool.normal;

import com.hgf.tool.common.model.constant.RegexConstant;

import java.util.regex.Pattern;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class PatternUtil {

    private PatternUtil() {
    }

    public static final Pattern NUMBER_REGEX = Pattern.compile(RegexConstant.NUMERICAL_REG);

    public static final Pattern PURE_NUMBER_REGEX = Pattern.compile(RegexConstant.PURE_NUMBER_REG);

    /**
     * 是否為純數字字符串
     *
     * @param value 字符串內容
     * @return 返回校驗結果
     */
    public static Boolean isNumberString(String value) {
        return PURE_NUMBER_REGEX.matcher(value).matches();
    }

    /**
     * 是否為數字字符串
     *
     * @param value 字符串內容
     * @return 返回校驗結果
     */
    public static Boolean isDecimalNumberString(String value) {
        return null != value && NUMBER_REGEX.matcher(value).matches();
    }

}

