package com.example.springbootrabbitmq.queues;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 声明消息队列.默认的durable = true(持久化)
 *
 * @author QuiFar
 * @version V1.0
 **/
@Configuration
public class QueueConfig {

    @Bean
    public Queue directQueue() {
        return new Queue("directQueue");
    }

    @Bean
    public Queue topicQueue() {
        return new Queue("topic.queue");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("exchange.topic");
    }

    /**
     * 公用的direct 类型交换器
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("comment.exchange.direct", false, false);
    }

    /**
     * 按照规则绑定队列
     * 基于消息的交换器类型和路由键，服务器会决定消息投送到哪个队列上去
     *
     * @param topicQueue    队列
     * @param topicExchange 需要绑定的交换器
     * @return
     */
    @Bean
    public Binding binding(Queue topicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("topic.#");
    }

    @Bean
    public Binding directBinding(Queue directQueue, TopicExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("directQueue");
    }


}
