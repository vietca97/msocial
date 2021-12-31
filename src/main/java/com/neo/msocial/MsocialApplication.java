package com.neo.msocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class MsocialApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsocialApplication.class, args);


	}

}
