package com.bms.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@EntityScan("com.bms.backend.entity")
@SpringBootApplication
public class BmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsBackendApplication.class, args);
	}

}
