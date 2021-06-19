package com.boot.springReddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.boot.springReddit.config.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class RedditSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditSpringApplication.class, args);
	}

}
