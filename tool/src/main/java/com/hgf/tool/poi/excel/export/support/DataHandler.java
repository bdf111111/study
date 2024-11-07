package com.hgf.tool.poi.excel.export.support;

import com.hgf.tool.poi.excel.export.ExportReflectiveCacheModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public interface DataHandler {

    /**
     * 往表格中插入数据
     * @param bodyStyle 数据体样式
     * @param cacheModelMap 缓存模型Map
     * @param row 数据表行
     * @param data 数据值
     * @param fieldName 字段名称
     */
    void setCellValue(CellStyle bodyStyle, Map<String, ExportReflectiveCacheModel> cacheModelMap, Row row, Object data, String fieldName);

    /**
     * 设备单元格数据类型
     * @param cell 单元格
     * @param fieldType 字段类型
     * @param value 字段值
     */
    void setCellType(Cell cell, Class<?> fieldType, Object value);
}
