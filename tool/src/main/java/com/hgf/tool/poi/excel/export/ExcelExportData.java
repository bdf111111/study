package com.hgf.tool.poi.excel.export;

import com.hgf.tool.poi.excel.annotation.ExcelColumn;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class ExcelExportData<T> {

    private static final Integer SHEET_MAX_LINE = Integer.MAX_VALUE;

    /**
     * 反射缓存
     */
    private List<String> fieldCaches = new ArrayList<>(16);
    private List<String> columnCaches = new ArrayList<>(16);
    private List<Integer> columnWidthCaches = new ArrayList<>(16);
    private Map<String, ExportReflectiveCacheModel> cacheModelMap = new ConcurrentHashMap<>(16);

    /**
     * 导出文件名称
     */
    private String fileName;
    /**
     * 导出表头标题
     */
    private String tableTitle;
    /**
     * 导出数据类型
     */
    private Class<T> classes;
    /**
     * 导出数据
     */
    private List<T> data;
    /**
     * 数据长度
     */
    private Integer dataCount;
    /**
     * 单个表格最大数据量
     */
    private Integer maxSheetCount;
    /**
     * 表格列数量
     */
    private Integer columnCount;
    /**
     * 高亮行下标
     */
    private Set<Integer> highLightRowIndexes;

    /**
     * Excel导出数据构造行数
     * @param fileName 导出文件名称
     * @param tableTitle Excel标题
     * @param classes 数据类类型
     * @param data 数据列表
     */
    public ExcelExportData(String fileName, String tableTitle, Class<T> classes, List<T> data) {
        this(fileName, tableTitle, classes, data, Collections.emptySet());
    }

    /**
     * Excel导出数据构造行数
     * @param fileName 导出文件名称
     * @param tableTitle Excel标题
     * @param classes 数据类类型
     * @param data 数据列表
     */
    public ExcelExportData(String fileName, String tableTitle, Class<T> classes, List<T> data, Set<Integer> highLightRowIndexes) {
        this.fileName = fileName;
        this.tableTitle = tableTitle;
        this.classes = classes;
        this.data = data;

        this.dataCount = data.size();
        this.columnCount = 0;
        this.maxSheetCount = SHEET_MAX_LINE;

        this.highLightRowIndexes = highLightRowIndexes;

        List<ExcelColumn> excelColumnList = new ArrayList<>(16);

        Stream.of(classes.getDeclaredFields()).forEach(field ->
                Optional.ofNullable(field.getAnnotation(ExcelColumn.class)).ifPresent(excelColumn -> {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    fieldCaches.add(fieldName);
                    excelColumnList.add(excelColumn);
                    cacheModelMap.put(fieldName, new ExportReflectiveCacheModel(field, field.getType(), excelColumn.position() - 1, fieldName, excelColumn));
                    this.setColumnCount(this.getColumnCount() + 1);
                })
        );

        excelColumnList.sort(Comparator.comparingInt(ExcelColumn::position));
        excelColumnList.forEach(excelColumn -> {
            columnCaches.add(excelColumn.columnName());
            columnWidthCaches.add(excelColumn.columnWidth());
        });
    }

    public List<String> getFieldCaches() {
        return fieldCaches;
    }

    public void setFieldCaches(List<String> fieldCaches) {
        this.fieldCaches = fieldCaches;
    }

    public List<String> getColumnCaches() {
        return columnCaches;
    }

    public void setColumnCaches(List<String> columnCaches) {
        this.columnCaches = columnCaches;
    }

    public List<Integer> getColumnWidthCaches() {
        return columnWidthCaches;
    }

    public void setColumnWidthCaches(List<Integer> columnWidthCaches) {
        this.columnWidthCaches = columnWidthCaches;
    }

    public Map<String, ExportReflectiveCacheModel> getCacheModelMap() {
        return cacheModelMap;
    }

    public void setCacheModelMap(Map<String, ExportReflectiveCacheModel> cacheModelMap) {
        this.cacheModelMap = cacheModelMap;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTableTitle() {
        return tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public Class<T> getClasses() {
        return classes;
    }

    public void setClasses(Class<T> classes) {
        this.classes = classes;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }

    public Integer getMaxSheetCount() {
        return maxSheetCount;
    }

    public void setMaxSheetCount(Integer maxSheetCount) {
        this.maxSheetCount = maxSheetCount;
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    public Set<Integer> getHighLightRowIndexes() {
        return highLightRowIndexes;
    }

    public void setHighLightRowIndexes(Set<Integer> highLightRowIndexes) {
        this.highLightRowIndexes = highLightRowIndexes;
    }
}
