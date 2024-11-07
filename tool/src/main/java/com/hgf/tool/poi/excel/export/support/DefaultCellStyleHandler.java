package com.hgf.tool.poi.excel.export.support;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class DefaultCellStyleHandler implements CellStyleHandler {

    /**
     * 生成带有边框的单元格样式
     * @param workbook excel对象
     * @return 返回单元格样式
     */
    @Override
    public CellStyle generatorBorderStyle(Workbook workbook) {
        CellStyle headStyle = workbook.createCellStyle();

        // 左边框
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        // 下边框
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBottomBorderColor(IndexedColors.BLACK.index);

        // 右边框
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setRightBorderColor(IndexedColors.BLACK.index);

        // 上边框
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setTopBorderColor(IndexedColors.BLACK.index);

        return headStyle;
    }
}

