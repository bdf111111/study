package com.hgf.study.easy_web.controller.file;

import com.hgf.tool.poi.excel.annotation.ExcelColumn;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class StudentRow {

    @ExcelColumn(columnName = "姓名", position = 1)
    private String name;

    @ExcelColumn(columnName = "年龄", position = 2)
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
