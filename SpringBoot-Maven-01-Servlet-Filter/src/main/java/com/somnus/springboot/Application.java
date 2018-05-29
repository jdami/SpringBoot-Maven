package com.somnus.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.somnus.springboot.filter.MyFilter;
import com.somnus.springboot.servlet.MyServlet;

@SpringBootApplication
//该注解会扫描相应的包
@ServletComponentScan
public class Application {
	
	@Bean
    public ServletRegistrationBean<MyServlet> MyServlet1(){
        return new ServletRegistrationBean<MyServlet>(new MyServlet(),"/servlet");
    }
	
	/**
     * 代码方式注册Bean
     * @return
     */
    @Bean
    public FilterRegistrationBean<MyFilter> setFilter(){
        FilterRegistrationBean<MyFilter> filterBean = new FilterRegistrationBean<MyFilter>();
        filterBean.setFilter(new MyFilter());
        filterBean.setName("myFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}