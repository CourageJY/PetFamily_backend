package com.pet.pay.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.pet.pay.mapper")
@EnableTransactionManagement //启用事务管理
public class MyBatisPlusConfig {


}
