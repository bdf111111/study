package com.hgf.tool.poi.excel.export.support;

import com.hgf.tool.poi.excel.export.ExportReflectiveCacheModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class DefaultDataHandler implements DataHandler {

    private final Logger log = LoggerFactory.getLogger(DefaultDataHandler.class);

    /**
     * 往表格中插入数据
     * @param bodyStyle     数据体样式
     * @param cacheModelMap 缓存模型Map
     * @param row           数据表行
     * @param data          数据值
     * @param fieldName     字段名称
     */
    @Override
    public void setCellValue(CellStyle bodyStyle, Map<String, ExportReflectiveCacheModel> cacheModelMap, Row row, Object data, String fieldName) {
        try {
            ExportReflectiveCacheModel exportReflectiveCacheModel = cacheModelMap.get(fieldName);
            Field field = exportReflectiveCacheModel.getField();

            Object value = field.get(data);

            Cell cell = row.createCell(exportReflectiveCacheModel.getSort());
            cell.setCellStyle(bodyStyle);

            // 设备单元格数据类型
            setCellType(cell, exportReflectiveCacheModel.getFieldType(), value);
        } catch (Exception e) {
            log.error("获取异常：" + e.getMessage(), e);
        }
    }

    /**
     * 设备单元格数据类型
     * @param cell      单元格
     * @param fieldType 字段类型
     * @param value     字段值
     */
    @Override
    public void setCellType(Cell cell, Class<?> fieldType, Object value) {
        if (null == value) {
            return;
        }
        if (Integer.class.isAssignableFrom(fieldType)) {
            cell.setCellValue((Integer) value);
        } else if (Long.class.isAssignableFrom(fieldType)) {
            cell.setCellValue((Long) value);
        } else if (Double.class.isAssignableFrom(fieldType)) {
            cell.setCellValue((Double) value);
        } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
