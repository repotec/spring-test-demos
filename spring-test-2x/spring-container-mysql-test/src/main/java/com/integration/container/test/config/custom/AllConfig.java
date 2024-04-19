package com.integration.container.test.config.custom;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BaseConfig.class})
public class AllConfig {

}
