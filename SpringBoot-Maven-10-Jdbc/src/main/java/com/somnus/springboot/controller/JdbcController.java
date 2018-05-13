package com.somnus.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.somnus.springboot.jdbc.dao.CityDao;
import com.somnus.springboot.jdbc.model.City;

@Controller
public class JdbcController {

	@Autowired
	private CityDao cityDao;

	@GetMapping("/jdbc")
	public String jpa(@RequestParam(name="cityName",defaultValue="上饶") String cityName, Model model) {
		City city = cityDao.findByCityName(cityName);
		model.addAttribute("city", city);
		return "jdbc";
	}

}
