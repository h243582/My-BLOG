package com.hyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //(exclude={DruidDataSourceAutoConfigure.class})
//@EnableEurekaClient  //开启注册中心客户端
@ComponentScan(value = "com.hyf.service",useDefaultFilters = true)
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);
    }
}
