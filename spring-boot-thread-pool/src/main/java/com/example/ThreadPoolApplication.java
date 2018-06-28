package com.example;

import com.example.task.HelloWorldTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThreadPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadPoolApplication.class, args);
    }

    @Bean
    public HelloWorldTask helloWorldTask() {
        return new HelloWorldTask(20);
    }
}
