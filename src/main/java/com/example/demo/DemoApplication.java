package com.example.demo;

import com.example.demo.service.impl.BusinessService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(DemoApplication.class, args);
		BusinessService bean = app.getBean(BusinessService.class);
		bean.selectAll();
	}
}
