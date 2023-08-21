package com.hgf.study.aop_read_write_separate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hgf.study.aop_read_write_separate.entity.Test;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 黄耿锋
 * @date 2023/8/17 10:33
 **/
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
