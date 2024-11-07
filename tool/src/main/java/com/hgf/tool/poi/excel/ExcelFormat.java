package com.hgf.tool.poi.excel;

import com.hgf.tool.poi.excel.exception.ExcelFormatErrorException;

import java.util.stream.Stream;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public enum ExcelFormat {

    /**
     * 2003
     */
    XLS,
    /**
     * 2007
     */
    XLSX;

    /**
     * 获取文件格式枚举
     * @param name 文件格式名称
     * @return 返回文件格式枚举
     */
    public static ExcelFormat getByName(String name) {
        return Stream.of(ExcelFormat.values())
                .filter(excelFormat -> excelFormat.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ExcelFormatErrorException("Excel文件格式错误: " + name));
    }
}

