package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.topic.TopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QuiFar
 * @version V1.0
 **/
@RestController
public class TopicController {

    @Autowired
    private TopicProducer producer;

    @GetMapping("/topic")
    public void sendMsg() {
        producer.send("exchange", "topic.23456469.sdfs.fsesf.sdfsd", "topic test");
    }

}
