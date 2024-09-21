package com.example.demo.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//모든 경로에 대해
		registry.addMapping("/**")
			//Origin이 로컬3000에 대해
			.allowedOrigins("http://localhost:3000","http://localhost:8081","http://prod-todo-ui-witwint.ap-northeast-2.elasticbeanstalk.com",
				"http://test-ui-service.s3-website.ap-northeast-2.amazonaws.com/")
			//GET,POST,PUT,PATCH,DELETE,OPTIONS 매서드 허용
			.allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}

}
