package kotoban;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import kotoban.domain.Word;
import kotoban.domain.WordRepository;
import kotoban.domain.User;
import kotoban.domain.UserRepository;

@SpringBootApplication
public class KotobanApplication {

    public static void main(String[] args) {
		SpringApplication.run(KotobanApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataFeed(WordRepository wordRepository, UserRepository userRepository) {
		return (params) -> {

			//Words
			Word word1 = new Word();
			word1.setJapanese("青い");
			word1.setReading("あおい");
			word1.setMeaning("sininen");
			wordRepository.save(word1);

			Word word2 = new Word();
			word1.setJapanese("赤い");
			word1.setReading("あかい");
			word1.setMeaning("punainen");
			wordRepository.save(word2);

			Word word3 = new Word();
			word1.setJapanese("緑");
			word1.setReading("もどり");
			word1.setMeaning("vihreä");
			wordRepository.save(word3);

			Word word4 = new Word();
			word1.setJapanese("黒い");
			word1.setReading("くろい");
			word1.setMeaning("musta");
			wordRepository.save(word4);

			Word word5 = new Word();
			word1.setJapanese("白い");
			word1.setReading("しろい");
			word1.setMeaning("valkoinen");
			wordRepository.save(word5);

			User user1 = new User();
			user1.setUsername("user");
			//password:user
			user1.setPasswordHash("$2a$10$/mc9hc6v0GA5q5MC.Ai43.vvC8yKpYuCTI0kGLQxr37lWTeHnY7.e");
			user1.setEmail("user@mail.com");
			user1.setRole("USER");
			userRepository.save(user1);
			
			User user2 = new User();
			user2.setUsername("admin");
			//password:admin
			user2.setPasswordHash("$2a$10$LhemIpLXLuxlrgc3xOqKfOFTzE4Z5pbL4Ow1kbKN0tKKh3IcAjrG6");
			user2.setEmail("admin@mail.com");
			user2.setRole("ADMIN");
			userRepository.save(user2);
			
			wordRepository.findAll();
		};
	}

}
