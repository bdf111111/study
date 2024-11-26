package com.hgf.tool.common.model.constant;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public final class DatePatternConstant {

    /**
     * 年月格式：yyyyMM
     */
    public static final String PURE_MONTH_PATTERN = "yyyyMM";

    /**
     * 日期格式：yyyyMMdd
     */
    public static final String PURE_DATE_PATTERN = "yyyyMMdd";

    /**
     * 年月反转格式：MMyyyy
     */
    public static final String PURE_MONTH_REVERSE_PATTERN = "MMyyyy";

    /**
     * 日期反转格式：ddMMyyyy
     */
    public static final String PURE_DATE_REVERSE_PATTERN = "ddMMyyyy";

    /**
     * 时间格式：HHmmss
     */
    public static final String PURE_TIME_PATTERN = "HHmmss";

    /**
     * 日期格式：yyyyMMddHHmmss
     */
    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

    /**
     * 日期格式：yyyyMMddHHmmssSSS
     */
    public static final String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 年月格式：yyyy/MM
     */
    public static final String DEFAULT_MONTH_PATTERN = "yyyy/MM";

    /**
     * 日期格式：yyyy/MM/dd
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy/MM/dd";

    /**
     * 年月反转格式：MM/yyyy
     */
    public static final String DEFAULT_MONTH_REVERSE_PATTERN = "MM/yyyy";

    /**
     * 日期反转格式：dd/MM/yyyy
     */
    public static final String DEFAULT_DATE_REVERSE_PATTERN = "dd/MM/yyyy";

    /**
     * 日期时间反转格式，精确到分：dd/MM/yyyy HH:mm
     */
    public static final String DEFAULT_DATETIME_MINUTE_REVERSE_PATTERN = "dd/MM/yyyy HH:mm";

    /**
     * 日期时间反转格式，精确到秒：dd/MM/yyyy HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_REVERSE_PATTERN = "dd/MM/yyyy HH:mm:ss";

    /**
     * 日期时间格式，精确到分：yyyy/MM/dd HH:mm
     */
    public static final String DEFAULT_DATETIME_MINUTE_PATTERN = "yyyy/MM/dd HH:mm";

    /**
     * 日期时间格式，精确到秒：yyyy/MM/dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy/MM/dd HH:mm:ss";

    /**
     * 日期时间格式，精确到毫秒：yyyy/MM/dd HH:mm:ss.SSS
     */
    public static final String DEFAULT_DATETIME_MS_PATTERN = "yyyy/MM/dd HH:mm:ss.SSS";

    /**
     * 年月格式：yyyy-MM
     */
    public static final String NORM_MONTH_PATTERN = "yyyy-MM";

    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 年月反转格式：MM-yyyy
     */
    public static final String NORM_MONTH_REVERSE_PATTERN = "MM-yyyy";

    /**
     * 日期反转格式：dd-MM-yyyy
     */
    public static final String NORM_DATE_REVERSE_PATTERN = "dd-MM-yyyy";

    /**
     * 日期时间反转格式：dd-MM-yyyy HH:mm
     */
    public static final String NORM_DATETIME_MINUTE_REVERSE_PATTERN = "dd-MM-yyyy HH:mm";

    /**
     * 时间格式：HH:mm:ss
     */
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";

    /**
     * 日期时间格式，精确到分：yyyy-MM-dd HH:mm
     */
    public static final String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * ISO8601日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss,SSS
     */
    public static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * HTTP头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z
     */
    public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    /**
     * JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
     */
    public static final String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String UTC_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static final String UTC_SIMPLE_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssXXX
     */
    public static final String UTC_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static final String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     */
    public static final String UTC_MS_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

}
