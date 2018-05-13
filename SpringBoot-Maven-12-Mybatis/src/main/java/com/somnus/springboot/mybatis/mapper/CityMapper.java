package com.somnus.springboot.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.somnus.springboot.mybatis.model.City;

@Mapper
public interface CityMapper {

    /**
    * 根据城市名称，查询城市信息
    *
    * @param cityName 城市名
    */
    @Select("select * from t_city_temp where city_name = #{cityName}")
    // 返回 Map 结果集
    @Results({
    	@Result(property = "id", column = "id"),
    	@Result(property = "cityName", column = "city_name"),
    	@Result(property = "description", column = "description")
    })
    City findByCityName(@Param("cityName") String cityName);
    
    @Insert("insert into t_city_temp (city_name, description) values (#{cityName}, #{description})")
    int saveCity(@Param("cityName") String cityName, @Param("description") String description);
    
    @Delete("delete from t_city_temp where city_name = #{cityName}")
    int removeByCityName(@Param("cityName") String cityName);

}
