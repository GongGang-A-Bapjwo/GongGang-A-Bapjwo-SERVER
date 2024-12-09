package com.example.gonggang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GonggangApplication {

	public static void main(String[] args) {
		SpringApplication.run(GonggangApplication.class, args);
	}

}
