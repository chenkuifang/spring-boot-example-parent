package com.example.demo.config;

import com.example.demo.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册servlet容器 配置
 * 作用：注册各个自定义的Servlet、Filter、Listener
 * 1. servlet 注册使用 ServletRegistrationBean
 * 2. Filter 注册使用 FilterRegistrationBean
 * 3. Listener 注册使用 ServletListenerRegistrationBean
 * 相应类均实现RegistrationBean类
 *
 * @author QuiFar
 * @version V1.0
 **/
@Configuration
public class RegistrationConfig {

    // 1.filter 过滤器注册方式
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");

        // 排除拦截
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico,/images/*,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

    // example to Servlet & Listener
    /*@Bean
    public ServletRegistrationBean getDemoServlet(){
        DemoServlet demoServlet = new DemoServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(demoServlet);
        List<String> urlMappings=new ArrayList<String>();
        urlMappings.add("/demoservlet");//访问，可以添加多个
        registrationBean.setUrlMappings(urlMappings);
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<EventListener> getDemoListener(){
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new DemoListener());
        //registrationBean.setOrder(1);
        return registrationBean;
    }*/

}
