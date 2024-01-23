package com.hgf.study.easy_web;

import com.hgf.study.easy_web.controller.TestController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huanggf
 * @date 2024/1/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private TestController testController;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }

    @Before
    public void before() {
        System.out.println("before");
    }

    @After
    public void after() {
        System.out.println("after");
    }

    @org.junit.Test
    public void test(){
        System.out.println(testController);
    }

    @org.junit.Test
    public void test2() {
        System.out.println("test2");
    }

}
