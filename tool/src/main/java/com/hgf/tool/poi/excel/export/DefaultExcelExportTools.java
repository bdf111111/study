package com.hgf.tool.poi.excel.export;

import com.hgf.tool.poi.excel.export.support.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class DefaultExcelExportTools extends AbstractExcelExportTools {

    private DataHandler dataHandler;
    private SheetHandler sheetHandler;
    private CellStyleHandler cellStyleHandler;

    public DefaultExcelExportTools() {
        this.dataHandler = new DefaultDataHandler();
        this.sheetHandler = new DefaultSheetHandler();
        this.cellStyleHandler = new DefaultCellStyleHandler();
    }

    public DefaultExcelExportTools(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
        this.sheetHandler = new DefaultSheetHandler();
        this.cellStyleHandler = new DefaultCellStyleHandler();
    }

    public DefaultExcelExportTools(SheetHandler sheetHandler) {
        this.dataHandler = new DefaultDataHandler();
        this.sheetHandler = sheetHandler;
        this.cellStyleHandler = new DefaultCellStyleHandler();
    }

    public DefaultExcelExportTools(DataHandler dataHandler, SheetHandler sheetHandler) {
        this.dataHandler = dataHandler;
        this.sheetHandler = sheetHandler;
        this.cellStyleHandler = new DefaultCellStyleHandler();
    }

    public DefaultExcelExportTools(DataHandler dataHandler, SheetHandler sheetHandler, CellStyleHandler cellStyleHandler) {
        this.dataHandler = dataHandler;
        this.sheetHandler = sheetHandler;
        this.cellStyleHandler = cellStyleHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public void setSheetHandler(SheetHandler sheetHandler) {
        this.sheetHandler = sheetHandler;
    }

    public void setCellStyleHandler(CellStyleHandler cellStyleHandler) {
        this.cellStyleHandler = cellStyleHandler;
    }

    /**
     * 生成字体
     *
     * @param workbook excel对象
     * @return 返回字体
     */
    @Override
    protected Font generatorFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("微软雅黑");
        return font;
    }

    /**
     * 生成标题样式
     *
     * @param workbook excel对象
     * @return 返回标题样式
     */
    @Override
    protected CellStyle generatorTitleStyle(Workbook workbook) {
        CellStyle headStyle = cellStyleHandler.generatorBorderStyle(workbook);

        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 粗体显示
        Font font = this.generatorFont(workbook);
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);

        headStyle.setFont(font);
        headStyle.setWrapText(true);

        return headStyle;
    }

    /**
     * 生成表头样式
     *
     * @param workbook excel对象
     * @return 返回表头样式
     */
    @Override
    protected CellStyle generatorHeadStyle(Workbook workbook) {
        CellStyle headStyle = cellStyleHandler.generatorBorderStyle(workbook);

        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 前景色
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setFillForegroundColor(IndexedColors.ORANGE.index);

        // 粗体显示
        Font font = this.generatorFont(workbook);
        font.setFontHeightInPoints((short) 11);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        font.setBold(true);

        headStyle.setFont(font);
        headStyle.setWrapText(true);

        return headStyle;
    }

    /**
     * 生成数据体样式
     *
     * @param workbook excel对象
     * @return 返回数据体样式
     */
    @Override
    protected CellStyle generatorBodyStyle(Workbook workbook) {
        CellStyle bodyStyle = cellStyleHandler.generatorBorderStyle(workbook);

        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 粗体显示
        Font font = this.generatorFont(workbook);
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);

        bodyStyle.setFont(font);
        bodyStyle.setWrapText(true);

        return bodyStyle;
    }

    /**
     * 生成高亮样式
     *
     * @param workbook excel对象
     * @return 返回数据体样式
     */
    @Override
    protected CellStyle generatorHighLightStyle(Workbook workbook) {
        CellStyle bodyStyle = cellStyleHandler.generatorBorderStyle(workbook);

        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 粗体显示
        Font font = this.generatorFont(workbook);
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());

        bodyStyle.setFont(font);
        bodyStyle.setWrapText(true);

        return bodyStyle;
    }

    /**
     * 数据处理
     *
     * @param excelExportData Excel导出数据
     * @param workbook        excel对象
     * @param titleStyle      标题样式
     * @param headStyle       表头样式
     * @param bodyStyle       数据体样式
     * @param highLightStyle  高亮样式
     */
    @Override
    protected void dataHandler(ExcelExportData<?> excelExportData, Workbook workbook, CellStyle titleStyle, CellStyle headStyle, CellStyle bodyStyle, CellStyle highLightStyle) {
        int sheetMaxLine = excelExportData.getMaxSheetCount();
        int sheetCount = excelExportData.getDataCount() > sheetMaxLine ? excelExportData.getDataCount() / sheetMaxLine + 1 : 1;

        List<String> fieldCaches = excelExportData.getFieldCaches();
        Map<String, ExportReflectiveCacheModel> cacheModelMap = excelExportData.getCacheModelMap();

        CompletableFuture<Void>[] futures = new CompletableFuture[sheetCount];

        for (int index = 1; index <= sheetCount; index++) {
            Integer finalIndex = 1 == index && 1 == sheetCount ? null : index;
            // 获取Excel表单
            Sheet sheet = sheetHandler.generatorSheet(workbook, titleStyle, headStyle, excelExportData, finalIndex);
            sheetHandler.setSheetHead(titleStyle, headStyle, excelExportData, sheet);

            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() ->
                    exportSheetData(excelExportData, bodyStyle, highLightStyle, fieldCaches, cacheModelMap, sheet, finalIndex));

            futures[index - 1] = completableFuture;
        }

        CompletableFuture.allOf(futures).join();
    }

    /**
     * 数据处理
     *
     * @param excelExportDataList Excel导出数据列表
     * @param workbook            excel对象
     * @param titleStyle          标题样式
     * @param headStyle           表头样式
     * @param bodyStyle           数据体样式
     * @param highLightStyle      高亮样式
     */
    @Override
    protected void dataHandlerList(List<ExcelExportData<?>> excelExportDataList, Workbook workbook, CellStyle titleStyle, CellStyle headStyle, CellStyle bodyStyle, CellStyle highLightStyle) {
        for (ExcelExportData<?> excelExportData : excelExportDataList) {
            int sheetMaxLine = excelExportData.getMaxSheetCount();
            int sheetCount = excelExportData.getDataCount() > sheetMaxLine ? excelExportData.getDataCount() / sheetMaxLine + 1 : 1;
            List<String> fieldCaches = excelExportData.getFieldCaches();
            Map<String, ExportReflectiveCacheModel> cacheModelMap = excelExportData.getCacheModelMap();

            CompletableFuture<Void>[] futures = new CompletableFuture[sheetCount];

            for (int index = 1; index <= sheetCount; index++) {
                Integer finalIndex = 1 == index && 1 == sheetCount ? null : index;
                // 获取Excel表单
                Sheet sheet = sheetHandler.generatorSheet(workbook, titleStyle, headStyle, excelExportData, finalIndex);
                sheetHandler.setSheetHead(titleStyle, headStyle, excelExportData, sheet);

                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() ->
                        exportSheetData(excelExportData, bodyStyle, highLightStyle, fieldCaches, cacheModelMap, sheet, finalIndex));

                futures[index - 1] = completableFuture;
            }

            CompletableFuture.allOf(futures).join();
        }
    }

    /**
     * 导出表单数据
     *
     * @param excelExportData Excel导出数据
     * @param bodyStyle       数据体样式
     * @param highLightStyle  高亮样式
     * @param fieldCaches     反射字段缓存
     * @param cacheModelMap   缓存模型Map
     * @param sheet           Excel表单
     * @param index           Excel表单下标
     */
    private void exportSheetData(ExcelExportData<?> excelExportData, CellStyle bodyStyle, CellStyle highLightStyle, List<String> fieldCaches, Map<String, ExportReflectiveCacheModel> cacheModelMap, Sheet sheet, Integer index) {
        // 获取数据开始角标
        int startIndex = null == index ? 0 : excelExportData.getMaxSheetCount() * (index - 1);
        int endIndex = null == index ? excelExportData.getMaxSheetCount() : excelExportData.getMaxSheetCount() * index;

        // 获取表单数据长度
        int size = excelExportData.getDataCount() < endIndex ? excelExportData.getDataCount() : endIndex;

        size = size - startIndex + DATA_BEGIN_LINE;

        Set<Integer> highLightRowIndexes = excelExportData.getHighLightRowIndexes();

        for (int line = DATA_BEGIN_LINE; line < size; line++) {
            Row row = sheet.createRow(line);
            int dataIndex = line + startIndex - DATA_BEGIN_LINE;
            Object data = excelExportData.getData().get(dataIndex);

            // 是否高亮
            CellStyle cellStyle = highLightRowIndexes.contains(dataIndex) ? highLightStyle : bodyStyle;

            fieldCaches.forEach(fieldName ->
                    // 往表格中插入数据
                    dataHandler.setCellValue(cellStyle, cacheModelMap, row, data, fieldName));
        }
    }
}
