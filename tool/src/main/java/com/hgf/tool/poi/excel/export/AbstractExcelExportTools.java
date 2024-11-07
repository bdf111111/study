package com.hgf.tool.poi.excel.export;

import com.hgf.tool.poi.excel.ExcelFormat;
import com.hgf.tool.poi.excel.ExcelTools;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public abstract class AbstractExcelExportTools implements ExcelTools {

    private final Logger log = LoggerFactory.getLogger(AbstractExcelExportTools.class);

    static final Integer DATA_BEGIN_LINE = 4;

    /**
     * 生成字体
     * @param workbook excel对象
     * @return 返回字体
     */
    protected abstract Font generatorFont(Workbook workbook);

    /**
     * 生成标题样式
     * @param workbook excel对象
     * @return 返回标题样式
     */
    protected abstract CellStyle generatorTitleStyle(Workbook workbook);

    /**
     * 生成表头样式
     * @param workbook excel对象
     * @return 返回表头样式
     */
    protected abstract CellStyle generatorHeadStyle(Workbook workbook);

    /**
     * 生成数据体样式
     * @param workbook excel对象
     * @return 返回数据体样式
     */
    protected abstract CellStyle generatorBodyStyle(Workbook workbook);

    /**
     * 生成高亮样式
     * @param workbook excel对象
     * @return 返回高亮样式
     */
    protected abstract CellStyle generatorHighLightStyle(Workbook workbook);

    /**
     * 数据处理
     * @param excelExportData Excel导出数据
     * @param workbook excel对象
     * @param titleStyle 标题样式
     * @param headStyle 表头样式
     * @param bodyStyle 数据体样式
     * @param highLightStyle 高亮样式
     */
    protected abstract void dataHandler(ExcelExportData<?> excelExportData, Workbook workbook, CellStyle titleStyle, CellStyle headStyle, CellStyle bodyStyle, CellStyle highLightStyle);

    /**
     * 處理數據列表
     * @param excelExportDataList Excel导出数据列表
     * @param workbook excel对象
     * @param titleStyle 标题样式
     * @param headStyle 表头样式
     * @param bodyStyle 数据体样式
     * @param highLightStyle 高亮样式
     */
    protected abstract void dataHandlerList(List<ExcelExportData<?>> excelExportDataList, Workbook workbook, CellStyle titleStyle, CellStyle headStyle, CellStyle bodyStyle, CellStyle highLightStyle);

    /**
     * 数据导出
     * @param excelExportData 导出数据
     * @return 返回ExcelWorkbook对象
     */
    public Workbook exportData(ExcelExportData<?> excelExportData) {
        // 首先校验导出文件格式
        ExcelFormat excelFormat = checkFileFormat(excelExportData.getFileName());

        // 增加数量判断，使用不同的导出工具类，提高导出速度
        Workbook workbook = ExcelFormat.XLS == excelFormat ? new HSSFWorkbook() : new SXSSFWorkbook(2000);

        try {
            // 生成标题样式
            CellStyle titleStyle = this.generatorTitleStyle(workbook);
            // 生成表头样式
            CellStyle headStyle = this.generatorHeadStyle(workbook);
            // 生成数据体样式
            CellStyle bodyStyle = this.generatorBodyStyle(workbook);
            // 生成高亮样式
            CellStyle highLightStyle = this.generatorHighLightStyle(workbook);

            // 数据处理
            this.dataHandler(excelExportData, workbook, titleStyle, headStyle, bodyStyle, highLightStyle);
        } catch (Exception e) {
            log.error("数据导出异常 <InterruptedException> :" + e.getMessage(), e);
        }

        return workbook;
    }

    /**
     * 数据导出，多个功能sheet
     * @param excelExportDataList 导出数据列表
     * @return 返回ExcelWorkbook对象
     */
    public Workbook exportDataList(List<ExcelExportData<?>> excelExportDataList) {
        // 首先校验导出文件格式
        ExcelFormat excelFormat = checkFileFormat(excelExportDataList.get(0).getFileName());

        // 增加数量判断，使用不同的导出工具类，提高导出速度
        Workbook workbook = ExcelFormat.XLS == excelFormat ? new HSSFWorkbook() : new SXSSFWorkbook(2000);

        try {
            // 生成标题样式
            CellStyle titleStyle = this.generatorTitleStyle(workbook);
            // 生成表头样式
            CellStyle headStyle = this.generatorHeadStyle(workbook);
            // 生成数据体样式
            CellStyle bodyStyle = this.generatorBodyStyle(workbook);
            // 生成高亮样式
            CellStyle highLightStyle = this.generatorHighLightStyle(workbook);

            // 数据处理
            this.dataHandlerList(excelExportDataList, workbook, titleStyle, headStyle, bodyStyle, highLightStyle);
        } catch (Exception e) {
            log.error("数据导出异常 <InterruptedException> :" + e.getMessage(), e);
        }

        return workbook;
    }
}

