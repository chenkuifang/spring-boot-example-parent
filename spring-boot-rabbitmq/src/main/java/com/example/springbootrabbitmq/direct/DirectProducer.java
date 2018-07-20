package com.example.springbootrabbitmq.direct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * exchange 类型为direct(default)
 * rabbitmq 的exchange 默认类型是 direct (根据routingKey 绑定到相应的队列)
 * exchange include 1.direct 2.topic 3.headers 4.fanout
 *
 * @author QuiFar
 * @version V1.0
 **/
@Component
public class DirectProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 往默认的交换器发送消息
     *
     * @param routingKey 绑定到队列的routing_key
     * @param msg        需要发送的消息
     */
    public void send(String routingKey, String msg) {
        System.err.println("producer send a message:" + msg);
        rabbitTemplate.convertAndSend(routingKey, msg);
    }
}
