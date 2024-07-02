package io.github.agajansahatov.utopia.api;

import io.github.agajansahatov.utopia.api.config.RsaKeyProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class Application{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder){
		return args -> {
			System.out.println();
			System.out.println("EncodedPassword('123456'): ".concat(passwordEncoder.encode("123456")));
			System.out.println();
			System.out.println("Hello World!!!");
			System.out.println();
		};
	}
}
