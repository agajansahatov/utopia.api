package com.utopia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@PropertySource("file:./application.properties")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
