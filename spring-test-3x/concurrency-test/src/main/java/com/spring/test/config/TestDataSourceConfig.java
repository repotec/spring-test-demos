package com.spring.test.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class TestDataSourceConfig {

    //@Bean
    //@Primary // Mark this as the primary data source for testing
    public DataSource testDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/hrdb?autoReconnect=true&useSSL=false") // Use an in-memory H2 database
                .username("root")
                .password("Root@12345")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}