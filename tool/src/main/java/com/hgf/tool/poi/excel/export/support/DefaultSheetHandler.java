package com.hgf.tool.poi.excel.export.support;

import com.hgf.tool.poi.excel.export.ExcelExportData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class DefaultSheetHandler implements SheetHandler {

    /**
     * 生成Excel表单
     * @param workbook        excel对象
     * @param titleStyle      标题样式
     * @param headStyle       表头样式
     * @param excelExportData excel导出数据
     * @param index           表单下标
     * @return 返回Excel表单
     */
    @Override
    public Sheet generatorSheet(Workbook workbook, CellStyle titleStyle, CellStyle headStyle, ExcelExportData excelExportData, Integer index) {
        String sheetName = excelExportData.getTableTitle();
        if (null != index) {
            sheetName += " - " + index;
        }
        return workbook.createSheet(sheetName);
    }

    /**
     * 设置表单头信息
     * @param titleStyle      标题样式
     * @param headStyle       表头样式
     * @param excelExportData excel导出数据
     * @param sheet           Excel表单
     */
    @Override
    public void setSheetHead(CellStyle titleStyle, CellStyle headStyle, ExcelExportData excelExportData, Sheet sheet) {

        int columnCount = excelExportData.getColumnCount();

        // 默认行高
        sheet.setDefaultRowHeight((short) (15 * 20));
        // 默认列宽
        sheet.setDefaultColumnWidth(15);

        // 指定列宽
        List<Integer> columnWidthCaches = excelExportData.getColumnWidthCaches();
        for (int column = 0; column < columnCount; column++) {
            sheet.setColumnWidth(column, columnWidthCaches.get(column) * 256 + 184);
        }

        // 设置标题行
        for (int line = 0; line < 3; line++) {
            Row row = sheet.createRow(line);

            for (int column = 0; column < columnCount; column++) {
                Cell cell = row.createCell(column);
                if (0 == line && 0 == column) {
                    cell.setCellValue(excelExportData.getTableTitle());
                }
                cell.setCellStyle(titleStyle);
            }
        }

        // noinspection unchecked
        List<String> columnCaches = excelExportData.getColumnCaches();
        Row columnRow = sheet.createRow(3);
        for (int column = 0; column < columnCount; column++) {
            Cell cell = columnRow.createCell(column);
            cell.setCellStyle(headStyle);
            cell.setCellValue(columnCaches.get(column));
        }

        // 合并单元格
        CellRangeAddress titleCellRangeAddress = new CellRangeAddress(0, 2, 0, columnCount - 1);

        sheet.addMergedRegion(titleCellRangeAddress);
    }
}

