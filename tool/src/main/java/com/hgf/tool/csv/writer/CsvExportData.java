package com.hgf.tool.csv.writer;

import com.hgf.tool.csv.annotation.CsvColumn;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * CSV导出数据模型
 */
public class CsvExportData<T> {

    /**
     * 反射缓存
     */
    private final List<String> fieldCaches = new ArrayList<>(16);

    /**
     * 模型缓存
     */
    private final Map<String, Field> cacheModelMap = new ConcurrentHashMap<>(16);

    /**
     * 表头缓存
     */
    private final List<String> columnCaches = new ArrayList<>(16);

    /**
     * 导出数据类型
     */
    private Class<T> classes;

    /**
     * 导出数据
     */
    private List<T> data;

    /**
     * 列数量
     */
    private Integer columnCount;

    public CsvExportData(Class<T> classes, List<T> data) {
        this.classes = classes;
        this.data = data;
        this.columnCount = 0;

        List<CsvColumn> csvColumnList = new ArrayList<>(16);

        Stream.of(classes.getDeclaredFields()).forEach(field ->
                Optional.ofNullable(field.getAnnotation(CsvColumn.class)).ifPresent(csvColumn -> {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    fieldCaches.add(fieldName);
                    csvColumnList.add(csvColumn);
                    cacheModelMap.put(fieldName, field);
                    columnCount++;
                })
        );

        csvColumnList.sort(Comparator.comparingInt(CsvColumn::position));
        csvColumnList.forEach(csvColumn -> {
            columnCaches.add(csvColumn.columnName());
        });
    }

    public List<String> getFieldCaches() {
        return fieldCaches;
    }

    public Map<String, Field> getCacheModelMap() {
        return cacheModelMap;
    }

    public List<String> getColumnCaches() {
        return columnCaches;
    }

    public Class<T> getClasses() {
        return classes;
    }

    public List<T> getData() {
        return data;
    }

    public Integer getColumnCount() {
        return columnCount;
    }
}