package com.hgf.tool.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

/**
 * @author huanggf
 * @date 2024/11/29
 */
public class JacksonUtil {

    /**
     * 转换为JSON字符串
     * @param obj 被转为JSON的对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        if (null == obj) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON字符串转为实体类对象，转换异常将被抛出
     * @param <T>        Bean类型
     * @param jsonString JSON字符串
     * @param beanClass  实体类对象
     * @return 实体类对象
     * @since 3.1.2
     */
    public static <T> T toBean(String jsonString, Class<T> beanClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(jsonString, beanClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON字符串转为列表，转换异常将被抛出
     * @param <T>        Bean类型
     * @param jsonString JSON字符串
     * @param beanClass  实体类对象
     * @return 列表
     */
    public static <T> List<T> toList(String jsonString, Class<T> beanClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, beanClass));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
