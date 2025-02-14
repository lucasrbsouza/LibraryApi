package io.github.lucasrbsouza.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //annotacion para o listener das classes do model funcioar
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
