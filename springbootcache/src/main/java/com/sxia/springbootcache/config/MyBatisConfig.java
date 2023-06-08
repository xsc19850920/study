package com.sxia.springbootcache.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 */
@Configuration
//@EnableTransactionManagement
@MapperScan({"com.sxia.mapper"})
public class MyBatisConfig {
}
