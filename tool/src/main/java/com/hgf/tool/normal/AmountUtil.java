package com.hgf.tool.normal;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Objects;

/**
 * @author huanggf
 * @date 2024/11/29
 */
public class AmountUtil {

    /**
     * 处理金额小数点，四舍五入
     *
     * @param amount 金额
     * @param currency 币种
     * @return 金额
     */
    public static BigDecimal handleAmountScale(BigDecimal amount, String currency) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        Currency instance = Currency.getInstance(currency);
        amount = amount.setScale(instance.getDefaultFractionDigits(), RoundingMode.HALF_UP);
        return amount;
    }

    /**
     * 金额千位符格式化
     *
     * @param amount 金额
     * @return 金额千位符格式化字符串
     */
    public static String amountThousandBitFormat(BigDecimal amount) {
        if (Objects.isNull(amount)) {
            return "";
        }
        int scale = amount.scale();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" + (scale > 0 ? "." : "")
                + "0".repeat(Math.max(0, scale)));
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(amount);
    }

    /**
     * 金额格式化（包括千位符，币种对应小数位）
     *
     * @param amount 金额
     * @param currency 币种
     * @return 金额格式化字符串
     */
    public static String amountFormat(BigDecimal amount, String currency) {
        amount = handleAmountScale(amount, currency);
        return amountThousandBitFormat(amount);
    }

}
