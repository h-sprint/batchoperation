package com.example.batchoperation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.batchoperation.mapper")

public class BatchoperationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchoperationApplication.class, args);
    }

}
