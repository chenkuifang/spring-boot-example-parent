package com.example.springbootrabbitmq.queues;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置消息队列
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
    public TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    /**
     * 按照规则绑定队列
     * routing_key 如何绑定到queue,由exchange的类型计算
     *
     * @param topicQueue 队列
     * @param exchange   需要绑定的交换器
     * @return
     */
    @Bean
    public Binding binding(Queue topicQueue, TopicExchange exchange) {
        return BindingBuilder.bind(topicQueue).to(exchange).with("topic.#");
    }
}
