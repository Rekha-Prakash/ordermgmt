package com.rekha.usecase.ordermgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("com.rekha")
@EnableMongoRepositories(basePackages = "com.rekha.usecase.ordermgmt.dao")
public class OrdermgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdermgmtApplication.class, args);
	}

}
