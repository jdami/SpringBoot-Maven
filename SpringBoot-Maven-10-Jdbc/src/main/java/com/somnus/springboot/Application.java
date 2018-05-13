package com.somnus.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.somnus.springboot.jdbc.dao.CityDao;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CityDao cityDao) {
		return (args) -> {
			cityDao.removeByCityName("上饶");
			cityDao.saveCity("上饶", "三清天下秀");
		};
	}

}