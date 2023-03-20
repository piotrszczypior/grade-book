package com.capgemini.gradebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootGradeBookApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGradeBookApp.class, args);
    }
}
