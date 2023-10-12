package com.hgf.study.shardings_phere.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hgf.study.shardings_phere.entity.Course;
import com.hgf.study.shardings_phere.entity.Test;
import com.hgf.study.shardings_phere.mapper.CourseMapper;
import com.hgf.study.shardings_phere.mapper.TestMapper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestMapper testMapper;

    @Resource
    private CourseMapper courseMapper;

    @GetMapping("/testa")
    public void a(){
        Test test = testMapper.findById(1);
        System.out.println(test);
    }

    @GetMapping("/testb")
    public void b(){
        Test test = testMapper.selectById(1);
        System.out.println(test);
    }

    @GetMapping("/testc")
    public void c(){
        Course course = new Course();
        course.setCname("1");
        Course course2 = new Course();
        course2.setCname("2");
        courseMapper.insert(course);
        courseMapper.insert(course2);
    }

    @GetMapping("/testd")
    public void d(){
        courseMapper.findByCid(1L);
        courseMapper.findByCid(2L);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 1);
        queryWrapper.eq("cname", "1");
        List<Course> courses = courseMapper.selectList(queryWrapper);
        System.out.println(1);
    }

}
