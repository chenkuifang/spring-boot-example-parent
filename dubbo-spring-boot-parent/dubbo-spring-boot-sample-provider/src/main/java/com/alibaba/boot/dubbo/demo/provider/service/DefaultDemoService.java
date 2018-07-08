package com.alibaba.boot.dubbo.demo.provider.service;

import com.alibaba.boot.dubbo.demo.consumer.DemoService;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * 接口实现类
 * 向注册中心曝露服务
 * Service注解配置曝露服务提供者，(内部封装了与注册中心的连接，以及开启服务端口)
 * 可以protocol，registry可以多个
 * <p>
 * note:发布一个服务最少需要：application、protocol、registry
 *
 * @author QuiFar
 * @since 1.0.0
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultDemoService implements DemoService {

    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }

}