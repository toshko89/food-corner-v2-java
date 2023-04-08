package com.example.food_corner_v2_java;

import com.example.food_corner_v2_java.auth.JwtKeyProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(JwtKeyProps.class)
@EnableScheduling
public class FoodCornerV2JavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodCornerV2JavaApplication.class, args);
	}

}

