package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.direct.DirectProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QuiFar
 * @version V1.0
 **/
@RestController
public class DirectController {

    @Autowired
    private DirectProducer producer;

    @GetMapping("/direct")
    public void sendMsg() {
        producer.send("hello", "hello world");
    }

}
