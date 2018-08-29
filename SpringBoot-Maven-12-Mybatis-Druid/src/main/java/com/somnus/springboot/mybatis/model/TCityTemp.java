package com.somnus.springboot.mybatis.model;

import javax.persistence.*;

@Table(name = "t_city_temp")
public class TCityTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city_name")
    private String cityName;

    private String description;
    
    public TCityTemp() {
		super();
	}

	public TCityTemp(String cityName, String description) {
		super();
		this.cityName = cityName;
		this.description = description;
	}

	/**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return city_name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}