package com.sparknetworks.loveos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
public class FilterMatchesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilterMatchesApplication.class, args);
    }
}
