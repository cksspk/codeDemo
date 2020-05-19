package com.ckss.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ckss.blog.home.**.mapper")
public class CBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CBlogApplication.class);
    }
}
