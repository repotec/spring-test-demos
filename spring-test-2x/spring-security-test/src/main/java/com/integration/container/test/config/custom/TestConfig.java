package com.integration.container.test.config.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
	@Bean
    public BaseConfig baseConfig() {
        return new BaseConfig();
    }
}
