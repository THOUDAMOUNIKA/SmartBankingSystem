package com.smartbank.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI smartBankOpenAPI()
	{
		return new OpenAPI()
				.info(new Info()
						.title("Smart Bank Management API")
						.version("1.0")
						.description("REST API for Customer, Account and Transaction Management")
						);
				
	}
}