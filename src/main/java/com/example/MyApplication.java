package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableWebSecurity

public class MyApplication {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/config")
	public String configPage(){
		return "config";
	}

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}

}