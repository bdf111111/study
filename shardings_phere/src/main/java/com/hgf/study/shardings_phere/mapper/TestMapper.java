package com.hgf.study.shardings_phere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hgf.study.shardings_phere.entity.Test;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends BaseMapper<Test> {

    Test findById(Integer id);

}




