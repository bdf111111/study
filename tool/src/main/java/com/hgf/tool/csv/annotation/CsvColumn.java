package com.hgf.tool.csv.annotation;

import com.hgf.tool.common.model.constant.StringConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Csv字段注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvColumn {

    /**
     * 字段名称
     *
     * @return 返回字段名称
     */
    String columnName() default StringConstant.EMPTY;

    /**
     * 位置
     * @return 默认99
     */
    int position() default 99;
}
