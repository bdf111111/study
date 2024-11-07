package com.hgf.tool.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 字段名称
     * @return 返回字段名称
     */
    String columnName() default "";

    /**
     * 列宽
     * @return 返回列宽
     */
    int columnWidth() default 15;

    /**
     * 位置
     * @return 默认99
     */
    int position() default 99;
}

