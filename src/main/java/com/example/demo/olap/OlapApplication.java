package com.example.demo.olap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OlapApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlapApplication.class, args);
    }

}
