package com.somnus.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.somnus.springboot.jpa.model.City;
import com.somnus.springboot.jpa.repository.CityRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CityRepository cityRepository) {
		return (args) -> {
			cityRepository.deleteAll();
			cityRepository.save(new City("九江", "庐山天下悠"));
		};
	}

}