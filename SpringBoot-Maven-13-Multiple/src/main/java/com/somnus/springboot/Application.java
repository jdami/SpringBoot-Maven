package com.somnus.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.somnus.springboot.multiple.primary.User;
import com.somnus.springboot.multiple.primary.UserRepository;
import com.somnus.springboot.multiple.secondary.Message;
import com.somnus.springboot.multiple.secondary.MessageRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(UserRepository userRepository,MessageRepository messageRepository) {
		return (args) -> {
			userRepository.save(new User("aaa", 10));
			userRepository.save(new User("bbb", 20));
			userRepository.save(new User("ccc", 30));
			userRepository.save(new User("ddd", 40));
			userRepository.save(new User("eee", 50));

			messageRepository.save(new Message("o1", "aaaaaaaaaa"));
			messageRepository.save(new Message("o2", "bbbbbbbbbb"));
			messageRepository.save(new Message("o3", "cccccccccc"));
		};
	}

}