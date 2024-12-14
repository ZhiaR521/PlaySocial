package com.zhiar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EntityScan(basePackages = "com.zhiar.entity")
@EnableJpaRepositories(basePackages = "com.zhiar.dao")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PlaySocial{

    public static void main(String[] args) {
        SpringApplication.run(PlaySocial.class, args);
    }
}