package com.dsc.dsclife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class DscApplication {

	public static void main(String[] args) {
		SpringApplication.run(DscApplication.class, args);
	}

}
