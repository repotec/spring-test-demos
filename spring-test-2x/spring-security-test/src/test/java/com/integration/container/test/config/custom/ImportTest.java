package com.integration.container.test.config.custom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({ BaseConfig.class })
public class ImportTest {

	@Autowired
    ApplicationContext context;
	
	@Test
    void givenImportedBeans_whenGettingEach_ShouldFindIt() {
		Assertions.assertTrue(context.containsBean("com.custom.config.com.spring.test.demo.BaseConfig"));
        Assertions.assertNotNull(context.getBean(BaseConfig.class));
    }
}
