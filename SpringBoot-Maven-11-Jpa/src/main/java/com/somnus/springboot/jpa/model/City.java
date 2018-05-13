package com.somnus.springboot.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_city")
public class City {
	
	/** 城市编号 */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/** 城市名称 */
	@Column(nullable = false,name="cityName")
	private String cityName;

	/** 描述 */
	@Column(nullable = false,name="description")
	private String description;
	
	public City() {
		super();
	}
	
	public City(String cityName, String description) {
		super();
		this.cityName = cityName;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}