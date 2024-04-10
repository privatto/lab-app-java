package com.jansouza.springboot.lab;

import java.util.logging.Logger;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LabApplication {

	public static final Logger logger = Logger.getLogger(LabApplication.class.getName());

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(LabApplication.class, args);

		logger.info("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			logger.info(beanName);
		}
	}
	
}