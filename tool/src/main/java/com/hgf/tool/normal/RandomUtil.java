package com.hgf.tool.normal;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class RandomUtil {
    public static final String BASE_NUMBER = "0123456789";
    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    public static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final String BASE_COMPLEX_CHAR_NUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private RandomUtil() {
    }

    public static String randomString(String baseString, int length) {
        if (StringUtil.isEmpty(baseString)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(length);
            if (length < 1) {
                length = 1;
            }

            int baseLength = baseString.length();

            for(int i = 0; i < length; ++i) {
                int number = randomInt(baseLength);
                sb.append(baseString.charAt(number));
            }

            return sb.toString();
        }
    }

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static int randomInt(int limit) {
        return getRandom().nextInt(limit);
    }

    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    public static String randomComplexString(int length) {
        return randomString(BASE_COMPLEX_CHAR_NUMBER, length);
    }

    public static String randomStringUpper(int length) {
        return randomString(BASE_CHAR_NUMBER, length).toUpperCase();
    }

    public static String randomNumbers(int length) {
        return randomString(BASE_NUMBER, length);
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String pureUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
