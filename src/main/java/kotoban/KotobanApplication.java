package kotoban;

import kotoban.domain.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import kotoban.domain.WordRepository;
import kotoban.domain.UserRepository;

@SpringBootApplication
public class KotobanApplication {

    private final CategoryRepository categoryRepository;

    KotobanApplication(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(KotobanApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataFeed(WordRepository wordRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return (args) -> {
			
		};
	}

}
