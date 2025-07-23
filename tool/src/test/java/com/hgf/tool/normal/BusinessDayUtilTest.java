package com.hgf.tool.normal;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BusinessDayUtilTest {

    @Test
    void testIsWeekend() {
        System.out.println(BusinessDayUtil.isWeekend(ZonedDateTime.now().plusDays(4)));
    }
}
