package com.elephant;

import com.cunw.boot.annotation.Boot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Boot(scanBasePackages = {"com.elephant"})
@ComponentScan(basePackages = {"com.elephant"})
@MapperScan(basePackages = {"com.elephant.*.mapper"})
@EnableFeignClients(basePackages = {"com.elephant.client"})
public class PuzzleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PuzzleApplication.class, args);
    }

}
