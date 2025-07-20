package com.hgf.tool.normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huanggf
 * @date 2024/11/29
 */
public class CollectionPageUtil {

    /**
     * 对List<T>类型做分页
     * @param list 要分页的List
     * @param current 当前页
     * @param pageSize 每页包含的行数
     * @param <T> List的泛型参数
     * @return 分页结果
     */
    public static <T> List<T> pageList(List<T> list, int current, int pageSize) {
        int totalCount = (list == null || list.size() == 0) ? 0 : list.size();
        return pageList(list,totalCount, current, pageSize);
    }

    /**
     * 对List<T>类型做分页
     * @param list 要分页的List
     * @param totalCount List总记录数
     * @param current 当前页
     * @param pageSize 每页包含的行数
     * @param <T> List的泛型参数
     * @return 分页结果
     */
    public static <T> List<T> pageList(List<T> list, int totalCount, int current, int pageSize) {
        if (list == null || list.size() == 0 || totalCount == 0) {
            return new ArrayList<>();
        }

        //开始索引（注意List的索引是从0开始）
        int fromIndex = (current - 1) * pageSize;
        //结束索引
        int toIndex = current * pageSize;

        if (fromIndex > totalCount) {
            //超过范围，返回空列表
            return new ArrayList<>();
        } else {
            //注意subList()方法不包含toIndex的元素
            if (toIndex > totalCount) {
                toIndex = totalCount;
            }
            return list.subList(fromIndex, toIndex);
        }
    }

}
