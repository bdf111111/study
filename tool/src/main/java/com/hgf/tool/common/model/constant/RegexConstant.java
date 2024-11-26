package com.hgf.tool.common.model.constant;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class RegexConstant {


    /**
     * 正则表达式：验证邮箱
     */
    public static final String EMAIL_REG = "^([a-z0-9A-Z][-_|.]*)+[\\w]?@([a-z0-9A-Z_-]+([a-z0-9A-Z_-]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证图片格式
     */
    public static final String IMAGE_REG = "^(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

    /**
     * 数字正则
     */
    public static final String PURE_NUMBER_REG = "^\\d+$";

    /**
     * 数字正则（含小数点）
     */
    public static final String NUMERICAL_REG = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * 字母数字正则
     */
    public static final String ENGLISH_NUM_REG = "[A-Za-z0-9]*";

    /**
     * 中文正则
     */
    public static final String CHINESE_REG = "[\\u4e00-\\u9fa5]+";

    /**
     * 非中文正则
     */
    public static final String NOT_CHINESE_REG = "^[^\\u4e00-\\u9fa5]+$";

    /**
     * 大陆手机号码正则
     */
    public static final String CN_PHONE_NUMBER_REG = "^(1[3-9])\\d{9}$";

    /**
     * 香港手机号码正则
     */
    public static final String HKG_PHONE_NUMBER_REG = "^\\d{8}$";

    /**
     * 公司电话正则
     */
    public static final String COMPANY_PHONE_NUMBER_REG = "^\\d{8,11}$";

    /**
     * 小写三十二位MD5正则
     */
    public static final String LOWER_THIRTY_TWO_MD5_REG = "^[a-z0-9]{32}$";

    /**
     * 八到三十个数字和字母正则
     */
    public static final String EIGHT_TO_THIRTY_NUMBER_AND_LETTER_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,30}$";

    /**
     * 银行号/分行号正则
     */
    public static final String BANK_CODE_REG = "^\\d{3}$";

    /**
     * 银行号码正则
     */
    public static final String BANK_NUMBER_REG = "^\\d{7,9}$";

    /**
     * 银行账号正则
     */
    public static final String BANK_ACCOUNT_REG = "^\\d{8,32}$";

    /**
     * 通联 - 英文名称正则 （半角）
     */
    public static final String OATS_EN_NAME_REG = "[\\x00-\\xff]*";

    /**
     * 通联 - 英文地址正则
     */
    public static final String OATS_EN_ADDRESS_REG = "[\\w/,* ]*";

    /**
     * 通联 - 中文地址正则
     */
    public static final String OATS_CN_ADDRESS_REG = "[\\u4e00-\\u9fa5\\w/,* ]*";

    /**
     * 商户中文名称正则
     */
    public static final String MERCHANT_NAME_REG = "^[a-zA-Z0-9_\\s\\u4e00-\\u9fa5~!@#$%*()-=+/\\\\',.]*$";

    /**
     * 商户英文名称正则
     */
    public static final String MERCHANT_EN_NAME_REG = "^[a-zA-Z0-9_\\s~!@#$%*()-=+/\\\\',.]*$";

    /**
     * 时间正则 HH:mm
     */
    public static final String HOUR_MINUTE_REG = "([0-1][0-9]|2[0-3]):([0-5][0-9])";

    /**
     * 6位数字
     */
    public static final String SIX_NUM_REG = "^\\d{6}$";

    /**
     * 8位数字
     */
    public static final String EIGHT_NUM_REG = "^\\d{8}$";

    /**
     * 数字字母正则
     */
    public static final String NUMBERS_AND_LETTER_REG = "^[a-zA-Z0-9]+$";

    /**
     * 日期正则 dd/MM/yyyy
     */
    public static final String DATE_REG = "^([1-9]|[0-2][0-9]|(3)[0-1])(\\/)(([1-9])|((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";

    /**
     * 应用版本号正则表达式
     */
    public static final String VERSION_REG = "^([1-9]\\d|\\d)(\\.([1-9]\\d|\\d)){1,2}$";

    /**
     * 应用包名正则表达式
     */
    public static final String PACKAGE_NAME_REG = "^([a-zA-Z_][a-zA-Z0-9_]*)+([.][a-zA-Z_][a-zA-Z0-9_]*)+$";

    /**
     * 区号正则表达式
     * （中国香港：852，中国大陆：86，中国澳门：853，中国台湾：886，新加坡：65，马来西亚：60）
     */
    public static final String AREA_CODE_REG = "^(852|86|853|65|886|60)$";
    
}
