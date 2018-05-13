package com.somnus.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.somnus.springboot.jpa.model.City;
import com.somnus.springboot.jpa.repository.CityRepository;

@Controller
public class JpaController {

	@Autowired
	private CityRepository repository;


	@GetMapping("/jpa")
	public String jpa(@RequestParam(name="cityName",defaultValue="九江") String cityName, Model model) {
		City city = repository.findCityByCityName(cityName);
		model.addAttribute("city", city);
		return "jpa";
	}

}
