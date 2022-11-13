package com.example.demo;

import com.example.demo.python.JythonCaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import jep.JepException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(DemoApplication.class, args);
//        JythonCaller app = new JythonCaller();
//        try {
//            System.out.println( app.searchImage() );
//
//
//
////                    ObjectMapper objectMapper = new ObjectMapper();
////        List<String> studentList = objectMapper.readValue(A, new TypeReference<List<String>>() {});
//        } catch (JepException e) {
//            e.printStackTrace();
//        }

//        System.out.println( A );

//        ObjectMapper objectMapper = new ObjectMapper();
//        List<String> studentList = objectMapper.readValue(A, new TypeReference<List<String>>() {});
//        System.out.println( studentList.get(1));
    }

}
