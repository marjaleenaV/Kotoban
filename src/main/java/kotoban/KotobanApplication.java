package kotoban;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import kotoban.domain.Word;
import kotoban.domain.WordRepository;
import kotoban.domain.Category;
import kotoban.domain.User;
import kotoban.domain.UserRepository;

@SpringBootApplication
public class KotobanApplication {

    public static void main(String[] args) {
		SpringApplication.run(KotobanApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataFeed(WordRepository wordRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return (params) -> {
			if (userRepository.findByUsername("user") == null ) {
			//Words
			Word word1 = new Word();
			word1.setJapanese("青い");
			word1.setReading("あおい");
			word1.setMeaning("blue");
			wordRepository.save(word1);

			Word word2 = new Word();
			word2.setJapanese("赤い");
			word2.setReading("あかい");
			word2.setMeaning("red");
			wordRepository.save(word2);

			Word word3 = new Word();
			word3.setJapanese("緑");
			word3.setReading("もどり");
			word3.setMeaning("green");
			wordRepository.save(word3);

			Word word4 = new Word();
			word4.setJapanese("黒い");
			word4.setReading("くろい");
			word4.setMeaning("black");
			wordRepository.save(word4);

			Word word5 = new Word();
			word5.setJapanese("白い");
			word5.setReading("しろい");
			word5.setMeaning("white");
			wordRepository.save(word5);

			User user1 = new User();
			user1.setUsername("user");
			user1.setPasswordHash(passwordEncoder.encode("user"));
			user1.setEmail("user@mail.com");
			user1.setRole("USER");
			userRepository.save(user1);
			
			User user2 = new User();
			user2.setUsername("admin");
			user2.setPasswordHash(passwordEncoder.encode("admin"));
			user2.setEmail("admin@mail.com");
			user2.setRole("ADMIN");
			userRepository.save(user2);
			
			
			wordRepository.findAll(); }
		};
	}

}
