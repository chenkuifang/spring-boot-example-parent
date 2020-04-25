package com.example.springbootrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }


//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> sftp =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        sftp.setConsumerFactory(consumerFactory());
//        // enable batch listening
//        sftp.setBatchListener(true);
//        return sftp;
//    }


}
