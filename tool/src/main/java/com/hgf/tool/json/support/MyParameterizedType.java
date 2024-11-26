package com.hgf.tool.json.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author huanggf
 * @date 2024/11/7
 */
public class MyParameterizedType implements ParameterizedType {
    /**
     * 类类型
     */
    private final Class<?> clazz;

    public MyParameterizedType(Class<?> clz) {
        clazz = clz;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
