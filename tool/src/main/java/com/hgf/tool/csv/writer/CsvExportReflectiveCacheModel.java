package com.hgf.tool.csv.writer;

import java.lang.reflect.Field;

/**
 * 导出反射缓存模型
 */
public class CsvExportReflectiveCacheModel {

    private Field field;
    private Integer sort;

    public CsvExportReflectiveCacheModel(Field field, Integer sort) {
        this.field = field;
        this.sort = sort;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
