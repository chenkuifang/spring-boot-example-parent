package com.alibaba.boot.dubbo.demo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动入口函数类
 *
 * @author QuiFar
 * @since 1.0.0
 */
@SpringBootApplication
//使dubbo的@Service等注解起效
@DubboComponentScan("com.alibaba.boot.dubbo.demo.provider.*")
public class DubboProviderDemo {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DubboProviderDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);

    }

}
