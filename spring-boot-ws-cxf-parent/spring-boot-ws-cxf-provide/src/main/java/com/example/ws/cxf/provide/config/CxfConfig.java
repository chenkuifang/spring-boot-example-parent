package com.example.ws.cxf.provide.config;

import com.example.ws.cxf.provide.interceptor.CxfAuthenticationInterceptor;
import com.example.ws.cxf.provide.interceptor.CxfResponseInterceptor;
import com.example.ws.cxf.provide.interceptor.MyFaultOutInterceptor;
import com.example.ws.cxf.provide.webservice.UserService;
import com.example.ws.cxf.provide.webservice.impl.UserServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * cxf 配置类
 *
 * @author Quifar
 * @version V1.0
 **/
@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean disServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), userService());
        endpoint.publish("/userService");

        // 添加请求拦截器
        endpoint.getInInterceptors().add(cxfAuthenticationInterceptor());

        endpoint.getOutFaultInterceptors().add(new MyFaultOutInterceptor());

        // 添加响应拦截器
        endpoint.getOutInterceptors().add(new CxfResponseInterceptor());
        return endpoint;
    }


    @Bean
    public CxfAuthenticationInterceptor cxfAuthenticationInterceptor() {
        return new CxfAuthenticationInterceptor();
    }
}
