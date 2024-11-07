package com.hgf.tool.poi.excel.export.support;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public interface CellStyleHandler {

    /**
     * 生成带有边框的单元格样式
     * @param workbook excel对象
     * @return 返回单元格样式
     */
    CellStyle generatorBorderStyle(Workbook workbook);
}
