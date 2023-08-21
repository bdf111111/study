package com.hgf.study.aop_read_write_separate.config;

/**
 * @author 黄耿锋
 * @date 2023/8/17 11:02
 **/
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void a() {
        set(DBTypeEnum.A);
        System.out.println("切换到a");
    }

    public static void b() {
        set(DBTypeEnum.B);
        System.out.println("切换到b");
    }

}
