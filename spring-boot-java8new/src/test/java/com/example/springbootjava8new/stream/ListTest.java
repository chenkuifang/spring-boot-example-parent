package com.example.springbootjava8new.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 1.stream操作分为 中间操作和终止操作，每个中间操作返回stream本身
 * 2.Stream 的创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set，Map不支持。
 * 3.Stream的操作可以串行执行或者并行执行。list.stream()获取串行流，list.parallelStream()获取并行流
 * <p>
 * 中间操作有：map,filter,distinct,sorted等
 * 终止操作有：foreach,count,Match,Reduce等
 *
 * @author Quifar
 * @version V1.0
 **/
public class ListTest {
    List<String> dataList = Arrays.asList("abc", "zkf", "ghi", "jk");

    // 1.foreach迭代
    @Test
    public void test1() {
        // 获取一个串行流 （parallelStream（）获取一个并行流）
        Stream<String> stream = dataList.stream();
        //stream.forEach((x) -> System.err.println(x));
        stream.forEach(System.out::println);
    }

    //2.map映射：map方法用于映射每个元素到对应的结果
    @Test
    public void test2() {
        Stream<String> stream = dataList.stream();

        stream.map((x) -> x + "quifar")
                .forEach(System.out::println);

    }

    //3.filter 方法用于通过设置的条件过滤出元素
    @Test
    public void test3() {
        Stream<String> stream = dataList.stream();

        stream.map((x) -> x + "quifar")
                .filter((x) -> x.contains("abc"))
                .forEach(System.err::println);
    }

    //4.limit 方法用于获取指定数量的流
    @Test
    public void test4() {
        Stream<String> stream = dataList.stream();

        stream.limit(2)
                .forEach(System.err::println);
    }

    //5.sorted 方法用于对流进行排序
    @Test
    public void test5() {
        Stream<String> stream = dataList.stream();

        stream.sorted()
                .forEach(System.err::println);
    }

    @Test
    public void test6() {
        boolean result = dataList.stream().anyMatch((x) -> x.equals("abc"));
        System.err.println(result);
    }

    // 串行流与并行流对比
    @Test
    public void test7() {
        List<String> values = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
    }

    @Test
    public void test8() {
        List<String> values = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long t0 = System.nanoTime();
        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }
}
