package com.example.mingchao_boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mingchao_boot.Mapper")
public class MingchaoBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MingchaoBootApplication.class, args);
    }

}
