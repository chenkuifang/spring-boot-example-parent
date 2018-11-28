package com.example.springbootjava8new;

import org.junit.Test;

import java.util.Optional;

/**
 * optional 测试
 * Optional 类对有可能为空的对象进行封装，减少NPE异常
 *
 * @author Quifar
 * @version V1.0
 **/
public class OptionalTest {

    @Test
    public void test1() {

        // 创建一个空的Optional对象
        Optional<String> op1 = Optional.empty();
        System.err.println(op1);// out put : Optional.empty

        // 创建一个带有值的Optional 对象
        Optional<String> op2 = Optional.of("quifar");
        System.err.println(op2); //Optional[quifar]
        System.err.println(op2.get()); //quifar

        // 判断对象是否存在
        System.err.println(op1.isPresent()); // false
        System.err.println(op2.isPresent()); // true

        // 如果Optional为空，指定默认返回值
        System.err.println(op1.orElse("aaa"));//aaa
        System.err.println(op2.orElse("bbb"));//quifar
    }
}
