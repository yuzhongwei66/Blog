package com.yu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.yu.mapper")
@SpringBootApplication
public class yuBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(yuBlogApplication.class,args);
    }

}
