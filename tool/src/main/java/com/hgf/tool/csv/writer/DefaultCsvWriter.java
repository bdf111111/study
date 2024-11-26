package com.hgf.tool.csv.writer;

import com.csvreader.CsvWriter;
import com.hgf.tool.normal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CSV写出工具抽象类
 */
public class DefaultCsvWriter {

    private static final Logger log = LoggerFactory.getLogger(DefaultCsvWriter.class);

    /**
     * 反引号符
     */
    private static final String BACK_QUOTE = "`";

    /**
     * 写入表头
     * @param csvWriter csv写入器
     * @param headers 表头信息
     * @throws IOException IO异常
     */
    public static void writeHeader(CsvWriter csvWriter, String[] headers) throws IOException {
        csvWriter.writeRecord(headers, false);
    }

    /**
     * 写入行数据
     * @param csvWriter csv写入器
     * @param bodyList 行数据
     * @throws IOException IO异常
     */
    public static void writeBody(CsvWriter csvWriter, List<String[]> bodyList) throws IOException {
        for (String[] bodyLine : bodyList) {
            csvWriter.writeRecord(bodyLine);
        }
    }

    /**
     * 导出CSV文件
     * @param fileName 文件名称
     * @param headers 表头
     * @param csvExportData csv数据
     * @return 返回csv文件
     * @throws IOException IO异常
     */
    public static File export(String fileName, String[] headers, CsvExportData<?> csvExportData) throws IOException {
        File file = new File(fileName);

        OutputStream outputStream = new FileOutputStream(file);

        //加BOM头，处理中文乱码
        outputStream.write(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF});
        CsvWriter csvWriter = new CsvWriter(outputStream, ',', StandardCharsets.UTF_8);

        // 写入表头
        writeHeader(csvWriter, headers);

        // 读取数据列表并写入数据
        readDataList(csvExportData, csvWriter);

        csvWriter.endRecord();
        csvWriter.flush();
        csvWriter.close();

        return file;
    }

    /**
     * 导出CSV文件
     *
     * @param fileName      文件名称
     * @param csvExportData csv数据
     * @return 返回csv文件
     * @throws IOException IO异常
     */
    public static File export(String fileName, CsvExportData<?> csvExportData) throws IOException {

        // 表头信息
        String[] headers = csvExportData.getColumnCaches().toArray(new String[]{});

        return export(fileName, headers, csvExportData);
    }

    /**
     * 读取数据列表并写入数据
     * @param csvExportData csv数据
     * @param csvWriter csv写入器
     * @throws IOException IO异常
     */
    private static void readDataList(CsvExportData<?> csvExportData, CsvWriter csvWriter) throws IOException {
        List<String[]> bodyList = new ArrayList<>();

        Map<String, Field> cacheModelMap = csvExportData.getCacheModelMap();
        List<String> fieldCaches = csvExportData.getFieldCaches();

        csvExportData.getData().forEach(data -> {
            String[] body = new String[csvExportData.getColumnCount()];

            for (int i = 0, size = fieldCaches.size(); i < size; i++) {
                String fieldName = fieldCaches.get(i);
                Field field = cacheModelMap.get(fieldName);
                try {
                    Object value = field.get(data);
                    String valueStr = (String) value;
                    body[i] = StringUtil.isNotEmpty(valueStr) ? BACK_QUOTE + valueStr : valueStr;
                } catch (IllegalAccessException e) {
                    log.error("数据生成异常:" + e.getMessage(), e);
                }
            }

            bodyList.add(body);
        });

        writeBody(csvWriter, bodyList);
    }
}
