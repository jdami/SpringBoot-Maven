package com.somnus.springboot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.somnus.springboot.jpa.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
	
	
	public City findCityByCityName(String cityName);
	
}