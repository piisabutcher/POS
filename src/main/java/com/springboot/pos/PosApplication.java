package com.springboot.pos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement   //开启事务管理
@ComponentScan("com.springboot")

public class PosApplication {
    public static void main(String[] args) {
        SpringApplication.run(PosApplication.class, args);
    }
}
