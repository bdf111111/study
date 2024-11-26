package com.hgf.study.easy_web.controller.file;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hgf.tool.csv.annotation.CsvColumn;
import com.hgf.tool.poi.excel.annotation.ExcelColumn;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class StudentRow {

    @CsvColumn(columnName = "姓名", position = 1)
    @ExcelColumn(columnName = "姓名", position = 1)
    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @CsvColumn(columnName = "年龄", position = 2)
    @ExcelColumn(columnName = "年龄", position = 2)
    @ExcelProperty(value = "年龄", index = 2)
    private String age;

    @ExcelProperty(value = "url", index = 3)
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
