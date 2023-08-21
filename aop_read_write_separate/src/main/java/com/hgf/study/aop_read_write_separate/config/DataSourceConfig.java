package com.hgf.study.aop_read_write_separate.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄耿锋
 * @date 2023/8/17 10:54
 **/
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.a.jdbc-url}")
    private String aJdbcUrl;
    @Value("${spring.datasource.a.driver-class-name}")
    private String aDriverClassName;
    @Value("${spring.datasource.a.username}")
    private String aUserName;
    @Value("${spring.datasource.a.password}")
    private String aPassword;

    @Value("${spring.datasource.b.jdbc-url}")
    private String bJdbcUrl;
    @Value("${spring.datasource.b.driver-class-name}")
    private String bDriverClassName;
    @Value("${spring.datasource.b.username}")
    private String bUserName;
    @Value("${spring.datasource.b.password}")
    private String bPassword;

//    @Primary
//    @Bean
//    public DataSource aDataSource() {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setUsername(aUserName);
//        hikariDataSource.setPassword(aPassword);
//        hikariDataSource.setJdbcUrl(aJdbcUrl);
//        hikariDataSource.setDriverClassName(aDriverClassName);
//        return hikariDataSource;
//    }
//
//    @Bean
//    public DataSource bDataSource() {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setUsername(bUserName);
//        hikariDataSource.setPassword(bPassword);
//        hikariDataSource.setJdbcUrl(bJdbcUrl);
//        hikariDataSource.setDriverClassName(bDriverClassName);
//        return hikariDataSource;
//    }

    @Primary
    @ConfigurationProperties("spring.datasource.a")
    @Bean
    public DataSource aDataSource() {
        return DataSourceBuilder.create().build();
    }

    @ConfigurationProperties("spring.datasource.b")
    @Bean
    public DataSource bDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource myRoutingDataSource(@Qualifier("aDataSource") DataSource aDataSource,
                                          @Qualifier("bDataSource") DataSource bDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.A, aDataSource);
        targetDataSources.put(DBTypeEnum.B, bDataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(aDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }

}