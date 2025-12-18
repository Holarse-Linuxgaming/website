package de.holarse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"de.holarse.backend.db.repositories"})
@EntityScan("de.holarse")
@ComponentScan("de.holarse")
public class HolaCmsApplication {

    public static void main(String[] args) {
		SpringApplication.run(HolaCmsApplication.class, args);
	}
    
}

