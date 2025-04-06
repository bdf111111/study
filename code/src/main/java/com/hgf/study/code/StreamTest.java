package com.hgf.study.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StreamTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        System.out.println(list.stream().allMatch(s -> Objects.equals(s, "a"))); //true
        System.out.println(list.stream().anyMatch(s -> Objects.equals(s, "a"))); //false
        System.out.println(list.stream().noneMatch(s -> Objects.equals(s, "a"))); //true
    }

}
