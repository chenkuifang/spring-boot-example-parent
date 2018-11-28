package com.example.springbootjava8new;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * lambda 表达式允许使用接口实现作为参数传递
 * 1.引入 -> 符号：lambda操作符，左边为参数列表，右边为lambda体（接口实现体）
 * 2.需要函数式接口的支持（只有一个抽象方法的接口，并且使用@FunctionInterface注解），内置主要有
 * 2.1 Function 函数型
 * 2.2 Consumer 消费型接口
 * 2.3 Predicate 断言
 * 2.4 Supplier 提供型
 *
 * @author Quifar
 * @version V1.0
 **/
public class LambdaTest {
    List<String> data = Arrays.asList("qq", "bb", "zz", "ee");

    @Test
    public void test1() {
        // JAVA8之前，对比两个数的大小 或许是这样做的
        //创建一个匿名的比较器对象Comparator然后将其传递给sort方法
        // 自然排序
        Collections.sort(data, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (String i : data) {
            System.err.println(i);
        }
    }

    @Test
    public void test2() {
        // 使用lambda表达式
        // 一般情况下 lambda参数列表的数据类型建议省略，java编译器会根据上下文推导出来
        // 对于lambda体内如果只有一行代码，可省略{}符号，也可省return
        Collections.sort(data, (x, y) -> x.compareTo(y));

        data.forEach(System.err::println);
    }
}
