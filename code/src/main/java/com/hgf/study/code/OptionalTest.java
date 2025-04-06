package com.hgf.study.code;

import cn.hutool.json.JSONUtil;

import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args) {
        Student student = new Student();
        String country = Optional.of(student)
                .map(Student::getAddress)
                .map(Address::getCountry)
                .orElseThrow(() -> {
                    System.out.println(JSONUtil.toJsonStr(student));
                    return new NullPointerException();
                });
    }

    static class Student {

        Student(){

        }
        private Address address;

        Address getAddress() {
            return address;
        }
    }

    class Address {
        private String country;

        String getCountry() {
            return country;
        }

    }

}
