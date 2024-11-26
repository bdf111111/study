package com.hgf.tool.easy.excel;

import com.alibaba.excel.write.handler.WriteHandler;

import java.util.List;
import java.util.Map;

public class SheetData {
    /**
     * sheet名称
     */
    private String sheetName;

    /**
     * 列表填充，无前缀
     */
    private List<?> list = null;

    /**
     * 写出的列表数据（对应模板的数据） 可以传递复杂的填充 多个不同的列表.
     * key：前缀，value：列表
     */
    private Map<String, List<?>> listsMap = null;

    /**
     * 填充普通字段。key：变量名，value：变量值
     */
    private Map<?, ?> map = null;

    /**
     * 处理器
     */
    private List<WriteHandler> writeHandlers = null;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Map<String, List<?>> getListsMap() {
        return listsMap;
    }

    public void setListsMap(Map<String, List<?>> listsMap) {
        this.listsMap = listsMap;
    }

    public Map<?, ?> getMap() {
        return map;
    }

    public void setMap(Map<?, ?> map) {
        this.map = map;
    }

    public List<WriteHandler> getWriteHandlers() {
        return writeHandlers;
    }

    public void setWriteHandlers(List<WriteHandler> writeHandlers) {
        this.writeHandlers = writeHandlers;
    }
}