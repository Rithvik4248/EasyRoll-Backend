package com.training.easypay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class EasypayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasypayApplication.class, args);
	}

}
