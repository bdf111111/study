package com.hgf.tool.poi.excel.export.support;

import com.hgf.tool.poi.excel.export.ExcelExportData;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public interface SheetHandler {

    /**
     * 生成Excel表单
     * @param workbook excel对象
     * @param titleStyle 标题样式
     * @param headStyle 表头样式
     * @param excelExportData excel导出数据
     * @param index 表单下标
     * @return 返回Excel表单
     */
    Sheet generatorSheet(Workbook workbook, CellStyle titleStyle, CellStyle headStyle, ExcelExportData excelExportData, Integer index);

    /**
     * 设置表单头信息
     * @param titleStyle 标题样式
     * @param headStyle 表头样式
     * @param excelExportData excel导出数据
     * @param sheet Excel表单
     */
    void setSheetHead(CellStyle titleStyle, CellStyle headStyle, ExcelExportData excelExportData, Sheet sheet);
}

