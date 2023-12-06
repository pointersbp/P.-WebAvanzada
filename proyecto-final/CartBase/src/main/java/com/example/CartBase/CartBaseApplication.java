package com.example.CartBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableEurekaClient
@EnableRetry
public class CartBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartBaseApplication.class, args);
	}

}
