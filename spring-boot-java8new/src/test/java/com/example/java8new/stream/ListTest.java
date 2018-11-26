package com.example.java8new.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * list 测试
 * 1.从List数据源的stream()方法中获取一个流
 *
 * @author Quifar
 * @version V1.0
 **/
public class ListTest {

    List<String> dataList = Arrays.asList("abc", "zkf", "ghi", "jk");

    @Test
    public void test1() {
        // 获取一个串行流 （parallelStream（）获取一个并行流）
        Stream<String> stream = dataList.stream();
        //stream.forEach((x) -> System.err.println(x));

        /********************流的操作**************************/

        // 1.foreach迭代
        //stream.forEach(System.out::println);

        //2.map映射：map方法用于映射每个元素到对应的结果
        //stream.map((x) -> x + "quifar")
        //        .forEach(System.out::println);


        //3.filter 方法用于通过设置的条件过滤出元素
        //stream.map((x) -> x + "quifar")
        //        .filter((x) -> x.contains("abc"))
        //        .forEach(System.err::println);

        //4.limit 方法用于获取指定数量的流
        //stream.limit(2)
        //       .forEach(System.err::println);

        //5.sorted 方法用于对流进行排序
        stream.sorted()
                .forEach(System.err::println);
    }
}
