package com.example.demo.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 1.处理用户信息获取逻辑接口 UserDetailsService
 * 2.处理用户校验的接口 UserDetails
 * 3.处理用户加密解密接口 PasswordEncoder
 *
 * @author Quifar
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据username 查找是否存在该用户
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查找数据库信息，然后获取数据库密码跟输入的密码加密后对比
        // 该操作应该是注册的时候加密存入数据库的
        String sqlInsertPwd = passwordEncoder.encode("123456");
        System.err.println("根据用户名：" + username + "查下数据库的密码是：" + sqlInsertPwd);

        // 实际情况下 密码和授权都是从数据库里读取的
        User user = new User(username, sqlInsertPwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
