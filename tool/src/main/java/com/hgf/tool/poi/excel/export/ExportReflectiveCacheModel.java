package com.hgf.tool.poi.excel.export;

import com.hgf.tool.poi.excel.annotation.ExcelColumn;

import java.lang.reflect.Field;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class ExportReflectiveCacheModel {

    private Field field;
    private Class<?> fieldType;
    private Integer sort;
    private String fieldName;
    private ExcelColumn excelColumn;

    public ExportReflectiveCacheModel(Field field, Class<?> fieldType, Integer sort, String fieldName, ExcelColumn excelColumn) {
        this.field = field;
        this.fieldType = fieldType;
        this.sort = sort;
        this.fieldName = fieldName;
        this.excelColumn = excelColumn;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public ExcelColumn getExcelColumn() {
        return excelColumn;
    }

    public void setExcelColumn(ExcelColumn excelColumn) {
        this.excelColumn = excelColumn;
    }
}

