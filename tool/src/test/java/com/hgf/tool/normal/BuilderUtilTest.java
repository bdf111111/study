package com.hgf.tool.normal;

import com.hgf.tool.json.JsonUtil;
import org.junit.jupiter.api.Test;

class BuilderUtilTest {

    @Test
    void testBuilder() {
        System.out.println(JsonUtil.toJsonString(BuilderUtil.of(ClassOne::new)
                .with(ClassOne::setName, "classOne").builder()));
    }
}
