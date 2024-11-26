package com.hgf.tool.normal;

import com.hgf.tool.common.model.constant.StringConstant;

import java.io.File;

/**
 * 文件名工具类
 */
public class FileNameUtil {

    private FileNameUtil() {
    }

    /**
     * 返回文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getName(File file) {
        return (null != file) ? file.getName() : null;
    }

    /**
     * 获取文件后缀名，扩展名不带“.”
     *
     * @param file 文件
     * @return 扩展名
     */
    public static String getSuffix(File file) {
        if (null == file || file.isDirectory()) {
            return null;
        }
        return extName(file.getName());
    }

    /**
     * 获得文件后缀名，扩展名不带“.”
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getSuffix(String fileName) {
        return extName(fileName);
    }

    /**
     * 返回主文件名
     *
     * @param file 文件
     * @return 主文件名
     */
    public static String getPrefix(File file) {
        if (file.isDirectory()) {
            return file.getName();
        }

        return mainName(file.getName());
    }

    /**
     * 返回主文件名
     *
     * @param fileName 完整文件名
     * @return 主文件名
     */
    public static String getPrefix(String fileName) {
        return mainName(fileName);
    }

    /**
     * 返回主文件名
     *
     * @param fileName 完整文件名
     * @return 主文件名
     */
    private static String mainName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(StringConstant.DOT);
        return (index == -1) ? fileName : fileName.substring(0, index);
    }


    /**
     * 获得文件的扩展名（后缀名），扩展名不带“.”
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    private static String extName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(StringConstant.DOT);
        return (index == -1) ? StringConstant.EMPTY : fileName.substring(index + 1);
    }

    /**
     * 校验文件后缀名
     *
     * @param fileName 文件名
     * @param extNames 被检查的扩展名数组
     * @return 是否是指定扩展名的类型
     */
    public static boolean isType(String fileName, String... extNames) {
        String extName = extName(fileName);
        if (null == extName || null == extNames || extNames.length < 1) {
            return false;
        }
        for (String value : extNames) {
            if (extName.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}

