package com.hgf.tool.easy.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <a href="https://easyexcel.opensource.alibaba.com/">点击进入EasyExcel官网</a>
 */
public class ExcelUtils {

    /**
     * 下载
     *
     * @param os        输出流
     * @param clazz     操作对象字节
     * @param data      数据
     * @param sheetName 表名
     */
    public static <T> void downLoad(OutputStream os, Class<T> clazz, List<T> data, String sheetName) {
        EasyExcelFactory.write(os, clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 读取，不处理图片，图片无法读取
     *
     * @param stream 输入流
     * @return List<T>
     */
    public static <T> List<T> excelRead(InputStream stream, Class<T> clazz, Integer headRowNumber) throws IOException {
        List<T> list = new ArrayList<>();
        EasyExcel.read(stream, clazz, new AnalysisEventListener<T>() {
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                list.add(t);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet().headRowNumber(headRowNumber).doRead();
        return list;
    }

    /**
     * 读取，可读取图片，需调用imgCallback()方法对图片进行处理并返回路径（不支持CSV文件导入，因为csv暂时无法处理图片）
     *
     * @param stream       输入流
     * @param callBack     imgCallback()方法图片处理后返回的路径
     * @param headRowCount 表头的行数(比如有些表格存在多行头,T为bean对象时无效,为Map时生效),
     *                     (当T为Map时, headRowCount不传默认为1,第一行是表头; 当T为bean对象时根据ExcelProperty配置字段计算)
     * @param heads        默认null，重新设置表头，且clazz的表头失效，只对T为bean对象生效，(特别注意: heads的表头顺序与clazz的字段顺序必须保持一致，否则bean赋值可能出错)
     */
    public static <T> List<T> excelImageRead(InputStream stream, Class<T> clazz, Integer headRowCount, List<List<String>> heads, ImageCallBack callBack) throws Exception {
        Workbook workbook = WorkbookFactory.create(stream); // 支持2003版本和2007版本
        Sheet sheet = workbook.getSheetAt(0); // 只处理第一个表
        // 如果是SXSSFWorkbook类型，转XSSFWorkbook类型，否则sheet.getRow可能为空
        if (workbook instanceof SXSSFWorkbook) {
            SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook) workbook;
            sheet = sxssfWorkbook.getXSSFWorkbook().getSheetAt(workbook.getSheetIndex(sheet));
        }
        ExcelTools.handleExcelImage(sheet, callBack); // 处理图片
        return ExcelTools.saveRowData(sheet, clazz, headRowCount, heads); // 存储并返回数据
    }

    /**
     * 填充Excel - 复杂的填充 -单个sheet
     *
     * @param sheetDataList sheet对应的填充信息
     * @param templateFile  excel模板文件
     * @apiNote <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/fill#复杂的填充">文献参考</a>
     */
    public static InputStream complexFillReturnByteArray(InputStream templateFile, List<SheetData> sheetDataList, ExcelTypeEnum excelType) throws Exception {
        if (Objects.isNull(templateFile)) {
            throw new Exception("templateFile is null");
        }
        if (Objects.isNull(sheetDataList) || sheetDataList.isEmpty()) {
            throw new Exception("sheetDataList is empty");
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(os).withTemplate(templateFile).excelType(excelType).build();

        sheetDataList.forEach(sheetData -> {
            String sheetName = sheetData.getSheetName();
            List<?> list = sheetData.getList();
            Map<?, ?> map = sheetData.getMap();
            Map<String, List<?>> listsMap = sheetData.getListsMap();
            List<WriteHandler> handlers = sheetData.getWriteHandlers();

            ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.writerSheet(sheetName);
            if (CollectionUtils.isNotEmpty(handlers)) {
                handlers.forEach(excelWriterSheetBuilder::registerWriteHandler);
            }

            WriteSheet writeSheet = excelWriterSheetBuilder.build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            if (Objects.nonNull(list) && !list.isEmpty()) {
                excelWriter.fill(list, fillConfig, writeSheet);
            }
            if (Objects.nonNull(listsMap)) {
                listsMap.forEach((k, v) -> excelWriter.fill(new FillWrapper(k, v), fillConfig, writeSheet));
            }
            if (Objects.nonNull(map) && !map.keySet().isEmpty()) {
                excelWriter.fill(map, writeSheet);
            }
        });
        excelWriter.finish();
        byte[] returns = os.toByteArray();
        os.close();
        return new ByteArrayInputStream(returns);
    }
}
