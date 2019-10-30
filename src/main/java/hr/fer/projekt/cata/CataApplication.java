package hr.fer.projekt.cata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CataApplication.class, args);
	}

}
