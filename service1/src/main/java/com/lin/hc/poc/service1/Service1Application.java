package com.lin.hc.poc.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Service1Application {

	public static void main(String[] args) {
		SpringApplication.run(Service1Application.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "Service One is running...";
	}

	@RequestMapping("/name")
	public String getName() {
		return "service1";
	}
}
