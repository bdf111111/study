package com.hgf.tool.normal;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class PatternUtilTest {

    private Logger log = LoggerFactory.getLogger(PatternUtilTest.class);

    @Test
    void testIsNumberString() {
        log.info(PatternUtil.isNumberString("value").toString());
    }

}
