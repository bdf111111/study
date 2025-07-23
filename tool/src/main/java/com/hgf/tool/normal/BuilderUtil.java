package com.hgf.tool.normal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 构建对象工具类
 * @author huanggf
 * @date 2024/12/2
 */
public class BuilderUtil<T> {
    /**
     * 构造函数
     */
    private final Supplier<T> constructor;

    /**
     * 属性构造器
     */
    private final List<Consumer<T>> fieldConsumers = new ArrayList<>();

    public BuilderUtil(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public static <T> BuilderUtil<T> of(Supplier<T> constructor) {
        return new BuilderUtil<>(constructor);
    }

    /**
     * 添加执行构建执行方法，执行参数
     *
     * @param fieldConsumer 属性设置的方法
     * @param params        参数值
     * @return 构造器
     */
    public <P> BuilderUtil<T> with(BiConsumer<T, P> fieldConsumer, P params) {
        fieldConsumers.add(currentObj -> fieldConsumer.accept(currentObj, params));
        return this;
    }

    /**
     * 构建对象
     *
     * @return 构建执行后的实例对象
     */
    public T builder() {
        T value = constructor.get();
        fieldConsumers.forEach(fieldConsumer -> fieldConsumer.accept(value));
        fieldConsumers.clear();
        return value;
    }
}
