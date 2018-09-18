package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Security配置
 *
 * @author Quifar
 * @version V1.0
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 创建一个用户信息(spring 5.0 开始不建议使用明文密码)
     * 等同于
     * spring.security.user.name = user
     * spring.security.user.password = 123456
     * spring.security.user.roles = USER
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$ZKwo/j3DE5qNQReaxFPWyuu6.zmH2do1cpZtk9ywhJ6nrOSwkHuGK")
                .roles("USER","ADMIN").build();
        manager.createUser(user);
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // and() 相当于xml的关闭标签
        http
                .authorizeRequests()
                .antMatchers("/index").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated() //其他URL要求用户进行身份验证
                .and()
                .formLogin();
    }
/*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }*/
}
