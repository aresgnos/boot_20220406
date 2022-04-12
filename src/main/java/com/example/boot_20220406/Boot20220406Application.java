package com.example.boot_20220406;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

// 정의 변수 설정
@PropertySource("classpath:global.properties")

// filter 적용하기
@ServletComponentScan(basePackages = { "com.example.filter" })

// 컨트롤러, 환경설정파일
@ComponentScan(basePackages = {
		"com.example.controller",
		"com.example.restcontroller",
		"com.example.config",
		"com.example.service",
		"com.example.jwt"

})

// mapper
@MapperScan(basePackages = { "com.example.mapper" })

// Entity(jpa) = DTO(mybatis)
@EntityScan(basePackages = { "com.example.entity" })

// 저장소(jpa)= Mapper
@EnableJpaRepositories(basePackages = { "com/example.repository" })
public class Boot20220406Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20220406Application.class, args);
	}

}
