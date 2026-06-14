package com.project.neurohelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NeurohelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeurohelpApplication.class, args);
	}

}
