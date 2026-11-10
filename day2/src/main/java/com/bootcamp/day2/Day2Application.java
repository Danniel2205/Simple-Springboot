package com.bootcamp.day2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Day2Application {

	public static void main(String[] args) {
		SpringApplication.run(Day2Application.class, args);
	}

}
