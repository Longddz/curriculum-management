package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"}) // Explicitly scan com.example and sub-packages
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000"); // Cho phép frontend ở cổng 3000
        config.addAllowedOrigin("http://127.0.0.1:5500"); // Cho phép frontend ở cổng 5500
        config.addAllowedHeader("*"); // Cho phép tất cả header
        config.addAllowedMethod("*"); // Cho phép tất cả method (GET, POST, PUT, DELETE, v.v.)
        config.setAllowCredentials(true); // Cho phép gửi cookie/credentials
        source.registerCorsConfiguration("/**", config); // Áp dụng cho tất cả endpoint
        return new CorsFilter(source);
    }
}