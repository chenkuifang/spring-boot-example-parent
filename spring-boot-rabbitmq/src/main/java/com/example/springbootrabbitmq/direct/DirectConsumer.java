package com.example.springbootrabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * exchange 类型为direct(default) 消费者
 *
 * @author QuiFar
 * @version V1.0
 **/
@Component
public class DirectConsumer {

    @RabbitListener(queues = "directQueue")
    public void processMessage(String msg) {
        System.err.println("consumer receive a message:" + msg);
    }
}
