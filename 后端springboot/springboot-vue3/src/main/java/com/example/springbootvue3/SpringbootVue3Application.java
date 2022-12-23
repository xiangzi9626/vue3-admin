package com.example.springbootvue3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootvue3.mapper")
public class SpringbootVue3Application {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootVue3Application.class, args);
	}
}
