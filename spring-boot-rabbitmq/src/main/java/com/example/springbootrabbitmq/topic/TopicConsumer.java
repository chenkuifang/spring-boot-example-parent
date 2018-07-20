package com.example.springbootrabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author QuiFar
 * @version V1.0
 **/
@Component
public class TopicConsumer {

    @RabbitListener(queues = "topic.queue")
    public void processMessage(String msg) {
        System.err.println("consumer receive a message:" + msg);
    }
}
