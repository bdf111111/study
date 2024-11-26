package com.hgf.tool.normal;


/**
 * 版本号工具类
 */
public class VersionUtil {

    private VersionUtil() {}

    /**
     * 比较版本号的大小，前者大则返回一个正数，后者大返回一个负数，相等则返回0
     *
     * @param version1 客户端版本号
     * @param version2 系统规定的最低版本号
     * @return 结果值
     */
    public static int compareVersion(String version1, String version2) {
        if (StringUtil.isEmpty(version1) || StringUtil.isEmpty(version2)) {
            return -1;
        }

        String[] a1 = version1.split("\\.");
        String[] a2 = version2.split("\\.");

        for (int n = 0; n < Math.max(a1.length, a2.length); n++) {
            int i = (n < a1.length ? Integer.parseInt(a1[n]) : 0);
            int j = (n < a2.length ? Integer.parseInt(a2[n]) : 0);
            if (i < j) {
                return -1;
            } else if (i > j) {
                return 1;
            }
        }
        return 0;
    }

}
