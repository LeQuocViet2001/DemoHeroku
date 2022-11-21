package com.example.demo;

import com.example.demo.python.JythonCaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import jep.JepException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
