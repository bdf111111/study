package com.hgf.tool.csv.reader;

import com.csvreader.CsvReader;
import com.hgf.tool.common.model.constant.StringConstant;
import com.hgf.tool.csv.annotation.CsvColumn;
import com.hgf.tool.normal.StringUtil;
import com.hgf.tool.poi.excel.exception.ExcelReadException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

/**
 * CSV读取工具抽象类
 */
public abstract class AbstractCsvReader {

    /**
     * 缓存模型集合
     */
    private final Map<Integer, Field> cacheModelMap = new HashMap<>(16);

    /**
     * tab键符号
     */
    private static final String TAB = "\t";

    /**
     * 读取工作簿信息
     * @return 返回工作簿实例
     */
    protected abstract CsvReader readCsv();

    /**
     * 从CSV读取数据列表
     * @param dataClass 数据实例类类型
     * @param <K> 读取数据类类型
     * @return 返回数据列表
     */
    public <K> List<K> readDataFormCsv(Class<K> dataClass) {
        // 初始化缓存模型集合
        initCacheModelMap(dataClass);

        // 读取工作簿
        CsvReader csvReader = readCsv();

        // 读取数据
        try {
            return readCvsData(dataClass, csvReader);
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            throw new ExcelReadException(e.getMessage(), e);
        }
    }

    /**
     * 读取Csv数据
     * @param dataClass 数据实例类类型
     * @param csvReader 工作簿
     * @param <K> 读取数据类类型
     * @return 返回数据列表
     * @throws IOException IO異常
     * @throws InstantiationException 实例化异常信息
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> List<K> readCvsData(Class<K> dataClass, CsvReader csvReader) throws IOException, InstantiationException, IllegalAccessException {
        // 读取数据
        List<K> list = new LinkedList<>();

        // 跳過表頭
        csvReader.readHeaders();

        while (csvReader.readRecord()) {
            readRowData(dataClass, csvReader.getValues(), list);
        }

        return list;
    }

    /**
     * 读取表格行数据
     * @param dataClass 数据实例类类型
     * @param recordValues 行數據
     * @param dataList 数据列表
     * @param <K> 读取数据类类型
     * @throws InstantiationException 实例化异常信息
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> void readRowData(Class<K> dataClass, String[] recordValues, List<K> dataList) throws InstantiationException, IllegalAccessException {
        if (null == recordValues) {
            return;
        }

        // 获取列数量
        int lastCellNum = recordValues.length;

        // 若整行数据全部为null或者空串""，则忽略该行。其中" "类型不属于其范畴
        boolean isAllNull = true;
        K instance = dataClass.newInstance();

        // 列下标从1开始
        for (int columnIndex = 1; columnIndex <= lastCellNum; columnIndex++) {
            if (readColumnData(instance, recordValues[columnIndex - 1], columnIndex)) {
                isAllNull = false;
            }
        }

        // 如果整行數據為空，則直接返回
        if (isAllNull) {
            return;
        }

        dataList.add(instance);
    }

    /**
     * 读取列数据
     * @param instance 数据类实例
     * @param value 列数据
     * @param columnIndex 列下标
     * @param <K> 读取数据类类型
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> boolean readColumnData(K instance, String value, int columnIndex) throws IllegalAccessException {
        final Field field = cacheModelMap.get(columnIndex);

        if (null == value || null == field) {
            return false;
        }

        // 过滤头尾空白字符
        value = value.trim();

        if (field.getType().equals(String.class)) {
            field.set(instance, value);
        } else if (field.getType().equals(Integer.class)) {
            field.set(instance, Integer.parseInt(value));
        } else if (field.getType().equals(Double.class)) {
            field.set(instance, Double.parseDouble(value));
        } else if (field.getType().equals(Float.class)) {
            field.set(instance, Float.parseFloat(value));
        }  else if (field.getType().equals(Long.class)) {
            field.set(instance, Long.parseLong(value));
        } else if (field.getType().equals(BigDecimal.class)) {
            field.set(instance, new BigDecimal(value));
        }

        return true;
    }

    /**
     * 初始化缓存模型集合
     * @param dataClass 数据实例类类型
     */
    private <K> void initCacheModelMap(Class<K> dataClass) {
        Stream.of(dataClass.getDeclaredFields()).forEach(field ->
                Optional.ofNullable(field.getAnnotation(CsvColumn.class)).ifPresent(csvColumn -> {
                    field.setAccessible(true);
                    cacheModelMap.put(csvColumn.position(), field);
                })
        );
    }

    /**
     * 读取Csv数据转换成键值对形式的list
     *
     * @return 返回数据列表
     * @throws IOException IO異常
     */
    public List<Map<String, String>> readCsvDataToListMap() throws IOException {

        // 读取工作簿
        CsvReader csvReader = readCsv();

        // 读取表頭
        csvReader.readHeaders();
        String[] headers = csvReader.getHeaders();

        // 读取数据
        List<Map<String, String>> list = new ArrayList<>();

        while (csvReader.readRecord()) {
            readRowDataToMap(headers, csvReader.getValues(), list);
        }

        return list;
    }

    /**
     * 读取表格行数据
     *
     * @param headers      表头数据
     * @param recordValues 行數據
     * @param dataList     数据列表
     */
    private void readRowDataToMap(String[] headers, String[] recordValues, List<Map<String, String>> dataList) {

        if (null == recordValues) {
            return;
        }

        // 列下标从0开始
        Map<String, String> lineMap = new HashMap<>();
        for (int index = 0; index < headers.length; index++) {

            String value = recordValues.length - 1 < index ? StringConstant.EMPTY : recordValues[index];

            lineMap.put(headers[index], clear(value));
        }

        dataList.add(lineMap);
    }

    /**
     * 清理字符串
     *
     * @param value value值
     * @return 清理后值
     */
    private String clear(String value) {

        if (StringUtil.isEmpty(value)) {
            return value;
        }

        // 替换 tab 键
        return value.replace(TAB, StringConstant.EMPTY);
    }
}
