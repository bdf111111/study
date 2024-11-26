package com.hgf.tool.normal;

import com.hgf.tool.common.model.constant.DatePatternConstant;
import com.hgf.tool.common.model.constant.StringConstant;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class TimeUtil {

    private TimeUtil() {}

    private static final DateTimeFormatter ISO_8601_DATE_PARSER_WALRUS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * 获取当前时间戳
     * @return 返回时间戳
     */
    public static long getCurrentTimestamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取时间毫秒值
     * @param zonedDateTime 时间
     * @return 时间毫秒值
     */
    public static long getMilliSeconds(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().toEpochMilli();
    }

    /**
     * 获取距离当天24点剩余毫秒值
     * @param zoneId 时区
     * @return 返回距离当天24点剩余毫秒值
     */
    public static long getTodayTimeRemainingMilliSeconds(ZoneId zoneId) {
        return getLastMillisecondOfDay(ZonedDateTime.now(zoneId)) - getCurrentTimestamp();
    }

    /**
     * 获取当天的开始时间
     * @param zonedDateTime 时间
     * @return 当天开始时间
     */
    public static ZonedDateTime getStartMillisecondOfDayZonedTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withHour(0).withMinute(0).withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);
    }

    /**
     * 获取当天的开始时间毫秒值
     * @param zonedDateTime 时间
     * @return 当天开始时间毫秒值
     */
    public static Long getStartMillisecondOfDay(ZonedDateTime zonedDateTime) {
        return getStartMillisecondOfDayZonedTime(zonedDateTime).toInstant().toEpochMilli();
    }


    /**
     * 获取当天的结束时间
     * @param zonedDateTime 时间
     * @return 当天结束时间
     */
    public static ZonedDateTime getLastMillisecondOfDayZonedTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withHour(23).withMinute(59).withSecond(59).with(ChronoField.MILLI_OF_SECOND, 999);
    }

    /**
     * 获取当天的结束时间毫秒值
     * @param zonedDateTime 时间
     * @return 当天结束时间毫秒值
     */
    public static Long getLastMillisecondOfDay(ZonedDateTime zonedDateTime) {
        return getLastMillisecondOfDayZonedTime(zonedDateTime).toInstant().toEpochMilli();
    }

    /**
     * 時間格式化：yyyyMMdd
     * @param zoneId 時區
     * @return 返回格式化後的時間字符串
     */
    public static String getCurrentTime(ZoneId zoneId) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePatternConstant.PURE_DATE_PATTERN);
        return getCurrentTime(zoneId, dateTimeFormatter);
    }

    /**
     * 時間格式化：yyyyMMdd
     * @param zoneId 時區
     * @param dateTimeFormatter 时间格式化格式
     * @return 返回格式化後的時間字符串
     */
    public static String getCurrentTime(ZoneId zoneId, DateTimeFormatter dateTimeFormatter) {
        return ZonedDateTime.now(zoneId).format(dateTimeFormatter);
    }

    /**
     * 時間格式化：dd/MM/yyyy HH:mm:ss
     * @param timestamp 時間戳
     * @param zoneId 時區
     * @return 返回格式化後的時間字符串
     */
    public static String formatTime(String timestamp, ZoneId zoneId) {
        return StringUtil.isEmpty(timestamp) ? StringConstant.EMPTY : formatTime(Long.parseLong(timestamp), zoneId);
    }

    /**
     * 時間格式化：dd/MM/yyyy HH:mm:ss
     * @param timestamp 時間戳
     * @param zoneId 時區
     * @return 返回格式化後的時間字符串
     */
    public static String formatTime(Long timestamp, ZoneId zoneId) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formatTime(timestamp, zoneId, dateTimeFormatter);
    }

    /**
     * 時間格式化
     * @param timestamp 時間戳
     * @param zoneId 時區
     * @param dateTimeFormatter 时间格式化格式
     * @return 返回格式化後的時間字符串
     */
    public static String formatTime(String timestamp, ZoneId zoneId, DateTimeFormatter dateTimeFormatter) {
        return StringUtil.isEmpty(timestamp) ? StringConstant.EMPTY : formatTime(Long.parseLong(timestamp), zoneId, dateTimeFormatter);
    }

    /**
     * 時間格式化
     *
     * @param timestamp         時間戳
     * @param zoneId            時區
     * @param dateTimeFormatter 时间格式化格式
     * @return 返回格式化後的時間字符串
     */
    public static String formatTime(Long timestamp, ZoneId zoneId, DateTimeFormatter dateTimeFormatter) {
        return null == timestamp ? StringConstant.EMPTY : getZonedTime(timestamp, zoneId).format(dateTimeFormatter);
    }

    /**
     * 時間格式化
     *
     * @param timestamp 時間戳
     * @param zoneId    時區
     * @return 返回格式化後的時間字符串
     */
    public static ZonedDateTime getZonedTime(Long timestamp, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId);
    }

    /**
     * 格式化時間
     *
     * @param zonedDateTime 時區時間
     * @return 返回格式化字符串
     */
    public static String formatTimeIso8601Walrus(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(ISO_8601_DATE_PARSER_WALRUS);
    }

    /**
     * 解析日期
     *
     * @param date          日期字符串
     * @param dateFormatter 日期格式化格式
     * @param zoneId        時區
     * @return 返回时间戳
     */
    public static long parseDate(String date, ZoneId zoneId, DateTimeFormatter dateFormatter) {
        return ZonedDateTime.of(LocalDate.parse(date, dateFormatter), LocalTime.MIDNIGHT, zoneId).toInstant().toEpochMilli();
    }

    /**
     * 解析日期时间
     *
     * @param dateTime          日期字符串
     * @param dateTimeFormatter 日期格式化格式
     * @param zoneId            時區
     * @return 返回时间戳
     */
    public static long parseDateTime(String dateTime, ZoneId zoneId, DateTimeFormatter dateTimeFormatter) {
        return ZonedDateTime.of(LocalDateTime.parse(dateTime, dateTimeFormatter), zoneId).toInstant().toEpochMilli();
    }
}
