package com.hgf.tool.normal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectMapperUtilTest {

    @Test
    void testToUnderlineJSONString() {
        // 对象转map/驼峰转下划线
        ClassOne classOne = new ClassOne();
        classOne.setName("classOne");
        List<ClassTwo> classTwoList = new ArrayList<>();
        ClassTwo classTwo1 = new ClassTwo();
        classTwo1.setName("classTwo1");
        classTwoList.add(classTwo1);
        ClassTwo classTwo2 = new ClassTwo();
        classTwo2.setName("classTwo2");
        classTwoList.add(classTwo2);
        classOne.setClassTwos(classTwoList);
        ObjectMapper objectMapper =new ObjectMapper();
        // 由于MAP的key和value是否泛型，会被类型擦除，所以使用TypeReference
        Map<String, Object> map =  objectMapper.convertValue(classOne, new TypeReference<>() {});
        System.out.println(map);
        System.out.println(ObjectMapperUtil.toUnderlineJSONString(classOne));
    }

}
