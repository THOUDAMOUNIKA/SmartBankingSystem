package com.smartbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(DatabaseConfig.class)
@EnableJpaRepositories(basePackages = "com.smartbank.repository")
public class RootConfig {
	

}
