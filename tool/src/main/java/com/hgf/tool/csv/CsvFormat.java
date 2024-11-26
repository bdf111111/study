package com.hgf.tool.csv;

import com.hgf.tool.poi.excel.exception.ExcelFormatErrorException;

import java.util.stream.Stream;

/**
 * CSV文件格式枚举
 */
public enum CsvFormat {

    /**
     * csv
     */
    CSV;

    /**
     * 获取文件格式枚举
     * @param name 文件格式名称
     * @return 返回文件格式枚举
     */
    public static CsvFormat getByName(String name) {
        return Stream.of(CsvFormat.values())
                .filter(excelFormat -> excelFormat.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ExcelFormatErrorException("csv文件格式错误: " + name));
    }
}
