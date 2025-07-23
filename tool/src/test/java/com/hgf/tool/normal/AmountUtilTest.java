package com.hgf.tool.normal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountUtilTest {

    @Test
    void testAmountFormat() {
        //金额格式化
        System.out.println(AmountUtil.amountFormat(new BigDecimal("1000000.11"), "JPY"));
    }
}
