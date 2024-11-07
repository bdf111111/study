package com.hgf.tool.poi.excel;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public interface ExcelTools {

    /**
     * 校验文件格式
     * @param fileName 文件名称
     * @return Excel文件格式枚举
     */
    default ExcelFormat checkFileFormat(String fileName) {
        String format = fileName.substring(fileName.lastIndexOf('.') + 1);
        return ExcelFormat.getByName(format);
    }
}
