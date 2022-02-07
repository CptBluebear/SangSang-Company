package org.corodiak.sangsang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SangSangApplication {

	public static void main(String[] args) {
		SpringApplication.run(SangSangApplication.class, args);
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                	.addMapping("/**")
                	.allowedOriginPatterns("*")
                	.allowedHeaders("*")
                	.allowedMethods("*")
                	.allowCredentials(true);
            }
        };
    }

}
