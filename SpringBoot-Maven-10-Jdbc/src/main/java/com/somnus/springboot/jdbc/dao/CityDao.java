package com.somnus.springboot.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.somnus.springboot.jdbc.model.City;

@Service
public class CityDao{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	public City findByCityName(String cityName) {
		return jdbcTemplate.queryForObject("select id, city_name, description from t_city_temp where city_name = ?", new Object[]{cityName},new RowMapper<City>(){

			@Override
			public City mapRow(ResultSet rs, int rowNum) throws SQLException {
				City city = new City();
				city.setId(rs.getLong("id"));
				city.setCityName(rs.getString("city_name"));
				city.setDescription(rs.getString("description"));
				return city;
			}
			
		});
	}

	public int saveCity(String cityName, String description) {
		return jdbcTemplate.update("insert into t_city_temp (city_name, description) values (?, ?)", cityName, description);
    };

	public int removeByCityName(String cityName) {
		return jdbcTemplate.update("delete from t_city_temp where city_name = ?", cityName);
	}

}