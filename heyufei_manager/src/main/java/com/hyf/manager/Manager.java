package com.hyf.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

@EnableZuulProxy
@SpringBootApplication
public class Manager {
    public static void main(String[] args) {
        SpringApplication.run(Manager.class, args);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
