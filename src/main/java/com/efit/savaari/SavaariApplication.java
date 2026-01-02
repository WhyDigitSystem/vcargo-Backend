package com.efit.savaari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class SavaariApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavaariApplication.class, args);
	}

}
