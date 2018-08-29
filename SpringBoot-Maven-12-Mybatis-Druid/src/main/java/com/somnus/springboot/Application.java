package com.somnus.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.somnus.springboot.mybatis.mapper.TCityTempMapper;
import com.somnus.springboot.mybatis.model.TCityTemp;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.somnus.springboot.mybatis.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(TCityTempMapper mapper) {
		return (args) -> {
			Example example = new Example(TCityTemp.class);
	        example.createCriteria().andEqualTo("cityName", "鹰潭");
			mapper.deleteByExample(example);
			
			mapper.insert(new TCityTemp("鹰潭","龙虎天下绝"));
		};
	}

}