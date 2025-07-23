package com.hgf.study.easy_web.demo.strategy;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author huanggf
 * @date 2025/7/22
 */
@Component
public class NumberStrategyFactory {

    private final Map<Integer, NumberStrategy> NUMBER_STRATEGY_MAP = new HashMap<>();

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        Map<String, NumberStrategy> beansOfType = applicationContext.getBeansOfType(NumberStrategy.class);
        beansOfType.forEach((k, v) -> NUMBER_STRATEGY_MAP.put(v.code(), v));
    }

    /**
     * 获取策略
     *
     * @param code 渠道编码
     * @return 策略
     */
    public NumberStrategy getStrategy(Integer code) {
        if (Objects.isNull(code) || !NUMBER_STRATEGY_MAP.containsKey(code)) {
            throw new RuntimeException("code is null or not found");
        }
        return NUMBER_STRATEGY_MAP.get(code);
    }

}
