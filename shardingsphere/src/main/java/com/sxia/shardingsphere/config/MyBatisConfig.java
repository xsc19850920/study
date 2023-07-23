package com.sxia.shardingsphere.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis相关配置
 */
@Configuration
//@EnableTransactionManagement
@MapperScan({"com.sxia.mapper.sharding"})
public class MyBatisConfig {
}
