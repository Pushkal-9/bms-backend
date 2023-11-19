package com.bms.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EntityScan("com.bms.backend.entity")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsBackendApplication.class, args);
	}

}
