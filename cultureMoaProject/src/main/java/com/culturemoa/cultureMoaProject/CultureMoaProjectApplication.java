package com.culturemoa.cultureMoaProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CultureMoaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureMoaProjectApplication.class, args);
	}

}
