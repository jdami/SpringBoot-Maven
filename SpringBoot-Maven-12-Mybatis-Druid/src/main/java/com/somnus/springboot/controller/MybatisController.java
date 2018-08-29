package com.somnus.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.somnus.springboot.mybatis.mapper.TCityTempMapper;
import com.somnus.springboot.mybatis.model.TCityTemp;

@Controller
public class MybatisController {

	@Autowired
	private TCityTempMapper mapper;


	@GetMapping("/mybatis")
	public String jpa(@RequestParam(name="cityName",defaultValue="鹰潭") String cityName, Model model) {
		TCityTemp condition = new TCityTemp();
		condition.setCityName("鹰潭");
		TCityTemp city = mapper.selectOne(condition);
		model.addAttribute("city", city);
		return "mybatis";
	}

}
