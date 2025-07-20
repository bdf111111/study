package com.hgf.tool.normal;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * @author huanggf
 * @date 2024/11/29
 */
public class PinyinUtil {

    /**
     * 中文字符串首字母排序
     * @param values 需要排序的字符串数组
     * @return 排序好的数组
     */
    public static String[] sort(String[] values){
        Comparator<Object> comparator = Collator.getInstance(Locale.CHINA);
        Arrays.sort(values, comparator);
        return values;
    }

    /**
     * 获取中文拼音字母（中国 -> Z）
     * @param str 需要转化的中文字符串
     * @return 第一个字的拼音
     */
    public static String getFirstChar(String str) {
        if(StringUtil.isNotEmpty(str)){
            StringBuilder convert = new StringBuilder();
            char word = str.charAt(0);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
            return convert.toString().toUpperCase();
        }else{
            return null;
        }
    }

    /**
     * 获取所有中文拼音字母（中国 -> ZG）
     * @param str 需要转化的中文字符串
     * @return 所有拼音字母缩写的字符串
     */
    public static String getAllChar(String str) {
        if(StringUtil.isNotEmpty(str)){
            StringBuilder convert = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                char word = str.charAt(j);
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
                if (pinyinArray != null) {
                    convert.append(pinyinArray[0].charAt(0));
                } else {
                    convert.append(word);
                }
            }
            return convert.toString().toUpperCase();
        }else{
            return null;
        }
    }

}
