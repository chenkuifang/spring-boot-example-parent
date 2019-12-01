package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置各个注册器
 *
 * @author quifar
 */
@Configuration
public class WebRegistryConfig implements WebMvcConfigurer {
    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/test/**")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/index")
//                .excludePathPatterns("/upload/**");

        //super.addInterceptors(registry);
    }

    /**
     * 定义文件上传的路径为spring boot的静态资源路径
     * 1.如果是类路径下的静态资源用classpath:定位
     * 2.如果是非类路径下，使用file:定位
     * 注意：静态资源请求是不过 spring mvc intercept 拦截器的
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 默认的classpath路径下的静态资源；
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/META-INF/resources/")
//                .addResourceLocations("classpath:/resources/")
//                .addResourceLocations("classpath:/static/")
//                .addResourceLocations("classpath:/public/");

        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/E:/upload-imgs/");
    }
}
