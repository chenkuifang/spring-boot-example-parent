package com.example.springbootjava8new.stream;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Map的Stream操作
 *
 * @author Quifar
 * @version V1.0
 **/
public class MapTest {
    private static final Map<String, Integer> data = new HashMap<>();

    {
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
    }

    /**
     * Stream对Map的元素排序
     */
    @Test
    public void test1() {
        data.entrySet()
                .parallelStream()
                .sorted((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()))
                .forEach(System.err::println);
    }

    /**
     * collect操作
     * Collectors工具类提供了toList,toSet,toMap,summingInt,partitioningBy(数据分区)，groupingBy(数据分组)等非常多的方法
     */
    @Test
    public void test2() {
        List<Map.Entry<String, Integer>> collect = data.entrySet().parallelStream()
                .limit(2)
                .collect(Collectors.toList());

        collect.forEach(x -> System.err.println(x.getValue())
        );

        Function<Integer, Integer> ageGroup = x -> x / 10;
    }

}
