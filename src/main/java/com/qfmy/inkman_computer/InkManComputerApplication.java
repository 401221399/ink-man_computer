package com.qfmy.inkman_computer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.qfmy.inkman_computer.dao")
@SpringBootApplication
public class InkManComputerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InkManComputerApplication.class, args);
    }

}
