package com.example.haldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL_FORMS;

@Configuration
@EnableHypermediaSupport(type = {HAL, HAL_FORMS})
@SpringBootApplication
public class HalDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(HalDemoApplication.class, args);
    }
}