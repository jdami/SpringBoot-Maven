package com.somnus.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.somnus.springboot.mybatis.mapper.CityMapper;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CityMapper mapper) {
		return (args) -> {
			mapper.removeByCityName("鹰潭");
			mapper.saveCity("鹰潭", "龙虎天下绝");
		};
	}

}