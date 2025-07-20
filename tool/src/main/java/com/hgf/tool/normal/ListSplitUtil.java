package com.hgf.tool.normal;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * list拆分工具
 */
public class ListSplitUtil {

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source 源集合
     * @param n      分成多少份
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        // 集合为空或 n <= 0 直接返回
        if (CollectionUtils.isEmpty(source) || n <= 0) {
            return result;
        }
        int remaider = source.size() % n;  // (先计算出余数)
        int number = source.size() / n;  // 然后是商
        int offset = 0; // 偏移量
        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}
