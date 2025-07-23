package com.hgf.tool.normal;

import java.util.List;

/**
 * @author huanggf
 * @date 2024/12/2
 */
public class ClassOne {

    private String name;

    private List<ClassTwo> classTwos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassTwo> getClassTwos() {
        return classTwos;
    }

    public void setClassTwos(List<ClassTwo> classTwos) {
        this.classTwos = classTwos;
    }
}
