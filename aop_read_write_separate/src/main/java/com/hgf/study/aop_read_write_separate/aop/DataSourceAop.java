package com.hgf.study.aop_read_write_separate.aop;

import com.hgf.study.aop_read_write_separate.config.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 黄耿锋
 * @date 2023/8/17 11:06
 **/
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(com.hgf.study.aop_read_write_separate.config.A) " +
            "|| (execution(* com.hgf.study.aop_read_write_separate.controller..*.a*(..)) " +
            "|| execution(* com.hgf.study.aop_read_write_separate.controller..*.select*(..)))")
    public void aPointcut() {

    }

    @Pointcut("!@annotation(com.hgf.study.aop_read_write_separate.config.A) " +
            "&& execution(* com.hgf.study.aop_read_write_separate.controller..*.b*(..)) " +
            "|| execution(* com.hgf.study.aop_read_write_separate.controller..*.write*(..)) ")
    public void bPointcut() {

    }

    @Before("aPointcut()")
    public void a() {
        DBContextHolder.a();
    }

    @Before("bPointcut()")
    public void b() {
        DBContextHolder.b();
    }

}
