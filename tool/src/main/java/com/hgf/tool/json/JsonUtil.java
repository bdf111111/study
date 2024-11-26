package com.hgf.tool.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hgf.tool.json.support.MyParameterizedType;

import java.util.List;

/**
 * @author huanggf
 * @date 2024/11/7
 */
public class JsonUtil {

    private JsonUtil() {}

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * JSON字符串转对象实例
     * @param data JSON字符串
     * @param tClass 类
     * @param <T> 泛型
     * @return 返回对象实例
     */
    public static <T> T parseObject(String data, Class<T> tClass) {
        return GSON.fromJson(data, tClass);
    }

    /**
     * JSON字符串转对象实例列表
     * @param data JSON字符串
     * @param tClass 类
     * @param <T> 泛型
     * @return 返回对象实例列表
     */
    public static <T> List<T> parseArray(String data, Class<T> tClass) {
        MyParameterizedType type = new MyParameterizedType(tClass);
        return GSON.fromJson(data, type);
    }

    /**
     * 对象实例转JSON字符串
     * @param object 对象实例
     * @return 返回JSON字符串
     */
    public static String toJsonString(Object object) {
        return GSON.toJson(object);
    }
}
