package com.hgf.tool.normal;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.function.Predicate;

/**
 * 工作日工具类
 */
public class BusinessDayUtil {

    private BusinessDayUtil() {
    }

    /**
     * 获取指定天数后的工作日时间，排除周末
     *
     * @param zonedDateTime 时间
     * @param intervalDays  间隔天数
     * @return 下一个工作日时间
     */
    public static ZonedDateTime getBusinessDayAfter(ZonedDateTime zonedDateTime, int intervalDays) {

        return getBusinessDayAfter(zonedDateTime, intervalDays, it -> !BusinessDayUtil.isWeekend(it));
    }

    /**
     * 获取指定天数后的工作日时间
     *
     * @param zonedDateTime        时间
     * @param intervalDays         间隔天数
     * @param businessDayPredicate 是否工作日判断
     * @return 下一个工作日时间
     */
    public static ZonedDateTime getBusinessDayAfter(ZonedDateTime zonedDateTime, int intervalDays, Predicate<ZonedDateTime> businessDayPredicate) {

        ZonedDateTime businessDay = zonedDateTime;

        for (int i = 0; i < intervalDays; i++) {
            businessDay = BusinessDayUtil.getNextBusinessDay(businessDay, businessDayPredicate);
        }

        return businessDay;
    }

    /**
     * 获取下一个工作日时间，排除周末
     *
     * @param zonedDateTime 时间
     * @return 工作日时间
     */
    public static ZonedDateTime getNextBusinessDay(ZonedDateTime zonedDateTime) {

        ZonedDateTime nextBusinessDay = zonedDateTime.plusDays(1);

        if (isWeekend(nextBusinessDay)) {
            nextBusinessDay = getNextBusinessDay(nextBusinessDay);
        }

        return nextBusinessDay;
    }

    /**
     * 获取下一个工作日时间
     *
     * @param zonedDateTime        时间
     * @param businessDayPredicate 是否工作日判断
     * @return 下一个工作日时间
     */
    public static ZonedDateTime getNextBusinessDay(ZonedDateTime zonedDateTime, Predicate<ZonedDateTime> businessDayPredicate) {

        ZonedDateTime nextBusinessDay = zonedDateTime.plusDays(1);

        if (!businessDayPredicate.test(nextBusinessDay)) {
            nextBusinessDay = getNextBusinessDay(nextBusinessDay, businessDayPredicate);
        }

        return nextBusinessDay;
    }

    /**
     * 计算间隔工作日天数（不足一日则不计算），排除周末
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 间隔工作日天数
     */
    public static long countIntervalBusinessDays(ZonedDateTime startTime, ZonedDateTime endTime) {

        return countIntervalBusinessDays(startTime, endTime, it -> !BusinessDayUtil.isWeekend(it));
    }

    /**
     * 计算间隔工作日天数（不足一日则不计算）
     *
     * @param startTime            开始时间
     * @param endTime              结束时间
     * @param businessDayPredicate 是否工作日判断
     * @return 间隔工作日天数
     */
    public static long countIntervalBusinessDays(ZonedDateTime startTime, ZonedDateTime endTime, Predicate<ZonedDateTime> businessDayPredicate) {

        startTime = startTime.withHour(0).withMinute(0).withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);
        endTime = endTime.withHour(0).withMinute(0).withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);

        ZonedDateTime nextDay = startTime.plusDays(1);

        int count = 0;
        while (endTime.compareTo(nextDay) > 0) {

            nextDay = nextDay.plusDays(1);

            if (businessDayPredicate.test(nextDay)) {
                ++count;
            }
        }

        return count;
    }

    /**
     * 判断是否为周末
     *
     * @param zonedDateTime 时间
     * @return 是否周末
     */
    public static boolean isWeekend(ZonedDateTime zonedDateTime) {

        DayOfWeek dayOfWeek = DayOfWeek.from(zonedDateTime);

        return DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
    }
}
