package com.example.springbootjava8new.stream;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Set stream 测试
 *
 * @author Quifar
 * @version V1.0
 **/
public class SetTest {

    //使用stream类来对map的value排序
    @Test
    public void test1() {
        Map<String, Integer> data = new HashMap<>();
        data.put("z", 10);
        data.put("b", 5);
        data.put("a", 6);
        data.put("c", 20);
        data.put("d", 1);
        data.put("e", 7);
        data.put("y", 8);
        data.put("n", 99);
        data.put("j", 50);
        data.put("m", 2);
        data.put("f", 9);

        data.entrySet()
                .stream()
                .sorted((x, y) -> -x.getValue().compareTo(y.getValue()))
                .forEach(System.err::println);
    }
}
