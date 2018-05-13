package com.somnus.springboot.jdbc.model;

public class City {
	
	/** 城市编号 */
	private Long id;

	/** 城市名称 */
	private String cityName;

	/** 描述 */
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