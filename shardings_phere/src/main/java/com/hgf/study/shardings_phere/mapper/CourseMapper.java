package com.hgf.study.shardings_phere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hgf.study.shardings_phere.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 黄耿锋
 * @date 2023/9/26 17:33
 **/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    Course findByCid(Long cid);

}
