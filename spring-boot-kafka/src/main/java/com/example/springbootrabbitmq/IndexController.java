package com.example.springbootrabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QuiFar
 * @version V1.0
 **/
@RestController
public class IndexController {

    @Autowired
    private Producer producer;

    @GetMapping("/index")
    public String index() {
        System.err.println("hello world");
        return "hello world";
    }

    @GetMapping("/send1")
    public void sendMsg() {
        System.err.println("send msg to test,test2");
        producer.send("test", "test");
        producer.send("test2", "test2");
    }

}
