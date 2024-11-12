package com.example.bankmanagementsystem;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling


public class BankManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankManagementSystemApplication.class, args);
    }
}