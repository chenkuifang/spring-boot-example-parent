package com.alibaba.boot.dubbo.demo.consumer;

import com.alibaba.boot.dubbo.demo.consumer.controller.DemoConsumerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动SpringBoot 入口函数
 *
 * @author QuiFar
 * @see DemoConsumerController
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = "com.alibaba.boot.dubbo.demo.*")
public class DubboConsumerDemo {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerDemo.class, args);
    }

}
