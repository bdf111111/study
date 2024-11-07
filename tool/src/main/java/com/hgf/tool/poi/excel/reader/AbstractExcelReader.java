package com.hgf.tool.poi.excel.reader;

import com.hgf.tool.poi.excel.annotation.ExcelColumn;
import com.hgf.tool.poi.excel.exception.ExcelReadException;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public abstract class AbstractExcelReader {

    /**
     * 缓存模型集合
     */
    private final Map<Integer, Field> cacheModelMap = new HashMap<>(16);

    /**
     * 读取工作簿信息
     * @return 返回工作簿实例
     */
    protected abstract Workbook readWorkbook();

    /**
     * 从Excel读取数据列表
     * @param dataClass 数据实例类类型
     * @param <K> 读取数据类类型
     * @param startRowNum 起始行号
     * @return 返回数据列表
     */
    public <K> List<K> readDataFormExcel(Class<K> dataClass, int startRowNum) {
        // 初始化缓存模型集合
        initCacheModelMap(dataClass);

        // 读取工作簿
        Workbook workbook = readWorkbook();

        // 读取数据
        try {
            return readExcelData(dataClass, workbook, startRowNum);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExcelReadException(e.getMessage(), e);
        }
    }

    /**
     * 从Excel读取数据列表
     * @param dataClass 数据实例类类型
     * @param <K> 读取数据类类型
     * @return 返回数据列表
     */
    public <K> List<K> readDataFormExcel(Class<K> dataClass) {
        // 2 表示跳过一行标题栏
        return readDataFormExcel(dataClass, 2);
    }

    /**
     * 读取Excel数据
     * @param dataClass 数据实例类类型
     * @param workbook 工作簿
     * @param <K> 读取数据类类型
     * @param startRowNum 起始行号
     * @return 返回数据列表
     * @throws InstantiationException 实例化异常信息
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> List<K> readExcelData(Class<K> dataClass, Workbook workbook, int startRowNum) throws InstantiationException, IllegalAccessException {
        if (startRowNum < 1) {
            throw new IllegalArgumentException("start row number must greater than 1");
        }
        // 获取单元表格数量
        int numberOfSheets = workbook.getNumberOfSheets();

        // 获取总的数据行数
        int totalRowSize = 0;
        for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
            totalRowSize += workbook.getSheetAt(sheetIndex).getLastRowNum() + 1;
        }

        // 读取数据
        List<K> list = new ArrayList<>(totalRowSize);
        for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
            readSheetRowData(dataClass, workbook, list, sheetIndex, startRowNum);
        }

        return list;
    }

    /**
     * 读取单个单元表格数据
     * @param dataClass 数据实例类类型
     * @param workbook 工作簿
     * @param dataList 数据列表
     * @param sheetIndex 胆原表格下表
     * @param <K> 读取数据类类型
     * @param startRowNum 起始行号
     * @throws InstantiationException 实例化异常信息
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> void readSheetRowData(Class<K> dataClass, Workbook workbook, List<K> dataList, int sheetIndex, int startRowNum) throws InstantiationException, IllegalAccessException {
        // 获取单元表格信息
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        // 获取最后一行行号
        int rowNumber = sheet.getLastRowNum() + 1;

        for (int rowIndex = startRowNum - 1; rowIndex < rowNumber; rowIndex++) {
            K instance = dataClass.newInstance();
            if (!readRowData(sheet, rowIndex, instance)) {
                dataList.add(instance);
            }
        }
    }

    /**
     * 读取行数据
     * @param sheet 单元表格信息
     * @param rowIndex 行下表
     * @param instance 数据类实例
     * @param <K> 读取数据类类型
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> boolean readRowData(Sheet sheet, int rowIndex, K instance) throws IllegalAccessException {
        // 获取行数据
        Row row = sheet.getRow(rowIndex);

        if (null == row) {
            return true;
        }

        // 获取读取列数量
        int readColumnNum = cacheModelMap.keySet().size();

        // 若整行数据全部为null或者空串""，则忽略该行。其中" "类型不属于其范畴
        boolean isAllNull = true;
        for (int columnIndex = 1; columnIndex <= readColumnNum; columnIndex++) {
            if (readColumnData(instance, row, columnIndex) && isAllNull) {
                isAllNull = false;
            }
        }

        return isAllNull;
    }

    /**
     * 读取列数据
     * @param instance 数据类实例
     * @param row 行数据
     * @param columnIndex 列下标
     * @param <K> 读取数据类类型
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> boolean readColumnData(K instance, Row row, int columnIndex) throws IllegalAccessException {
        final Field field = cacheModelMap.get(columnIndex);
        final Cell cell = row.getCell(columnIndex - 1);

        if (cell == null) {
            return false;
        }

        if (CellType.NUMERIC.equals(cell.getCellType())) {
            setFieldNumberValue(field, instance, cell.getNumericCellValue());
        } else if (CellType.STRING.equals(cell.getCellType())) {
            setFieldNormalValue(field, instance, cell.getStringCellValue());
        } else if (CellType.BOOLEAN.equals(cell.getCellType())) {
            setFieldNormalValue(field, instance, cell.getBooleanCellValue());
        } else if (CellType.ERROR.equals(cell.getCellType())) {
            setFieldNormalValue(field, instance, cell.getErrorCellValue());
        } else if (CellType.BLANK.equals(cell.getCellType())) {
            setFieldNormalValue(field, instance, "");
            return false;
        }

        return true;
    }

    /**
     * 设置实例字段普通数值
     * @param field 字段实例
     * @param instance 数据类实例
     * @param value 数据值
     * @param <K> 读取数据类类型
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> void setFieldNumberValue(Field field, K instance, double value) throws IllegalAccessException {
        if (Integer.class.isAssignableFrom(field.getType())) {
            field.set(instance, (int) value);
        } else if (Float.class.isAssignableFrom(field.getType())) {
            field.set(instance, (float) value);
        } else if (Double.class.isAssignableFrom(field.getType())) {
            field.set(instance, value);
        } else if (Long.class.isAssignableFrom(field.getType())) {
            field.set(instance, (long) value);
        } else if (String.class.isAssignableFrom(field.getType())) {
            field.set(instance, new BigDecimal(String.valueOf(value)).stripTrailingZeros().toPlainString());
        }
    }

    /**
     * 设置实例字段普通数值
     * @param field 字段实例
     * @param instance 数据类实例
     * @param value 数据值
     * @param <K> 读取数据类类型
     * @throws IllegalAccessException 非法访问异常信息
     */
    private <K> void setFieldNormalValue(Field field, K instance, Object value) throws IllegalAccessException {
        field.set(instance, value);
    }

    /**
     * 初始化缓存模型集合
     * @param dataClass 数据实例类类型
     */
    private <K> void initCacheModelMap(Class<K> dataClass) {
        Stream.of(dataClass.getDeclaredFields()).forEach(field ->
                Optional.ofNullable(field.getAnnotation(ExcelColumn.class)).ifPresent(excelColumn -> {
                    field.setAccessible(true);
                    cacheModelMap.put(excelColumn.position(), field);
                })
        );
    }
}
