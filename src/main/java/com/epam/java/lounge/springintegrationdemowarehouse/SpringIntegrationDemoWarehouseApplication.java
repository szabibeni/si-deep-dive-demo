package com.epam.java.lounge.springintegrationdemowarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class SpringIntegrationDemoWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationDemoWarehouseApplication.class, args);
	}

}
