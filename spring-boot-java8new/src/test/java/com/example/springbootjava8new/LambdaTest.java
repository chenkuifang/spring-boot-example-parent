package com.example.springbootjava8new;

import org.junit.Test;

import java.util.*;

/**
 * lambda 表达式允许使用接口实现作为参数传递
 * 1.引入 -> 符号：lambda操作符，左边为参数列表，右边为lambda体（接口实现体）
 * 2.需要函数式接口的支持（只有一个抽象方法的接口，并且使用@FunctionInterface注解），内置主要有
 * 2.1 Function 函数型  接受一个输入参数，返回一个结果。
 * 2.2 Consumer 消费型接口 代表了接受一个输入参数并且无返回的操作
 * 2.3 Predicate 断言 接受一个输入参数，返回一个布尔值结果。
 * 2.4 Supplier 提供型 无参数，返回一个结果。
 *
 * @author Quifar
 * @version V1.0
 **/
public class LambdaTest {
    List<String> data = Arrays.asList("a", "z", "c", "e");

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

    /**
     * sort操作，属于中间操作
     */
    @Test
    public void test2() {
        // 使用lambda表达式
        // 一般情况下 lambda参数列表的数据类型建议省略，java编译器会根据上下文推导出来
        // 对于lambda体内如果只有一行代码，可省略{}符号，也可省return
        Collections.sort(data, (x, y) -> x.compareTo(y));

        data.forEach(System.err::println);
    }

    /**
     * filter操作，filter属于中间操作，返回Stream ，所以还可以做其他的Stream操作
     * foreach操作属于最终操作，某个流执行了最终操作后，就不能再做其他的Stream操作了
     */
    @Test
    public void test3() {
        data.parallelStream()
                .filter(x -> x.startsWith("q")) // 中间操作
                .forEach(System.err::println); //  最终操作
    }

    /**
     * 中间操作map会将元素根据指定的Function接口来依次将元素转成另外的对象.
     */
    @Test
    public void test4() {
        data.parallelStream()
                .map(String::toUpperCase)
                .map(x -> x + "quifar")
                .sorted(String::compareTo)
                .forEach(System.err::println);
    }

    /**
     * match操作、count操作属于最终操作
     */
    @Test
    public void test5() {
        boolean a = data.stream().anyMatch(x -> x.equals("a"));
        System.err.println(a);

        long count = data.stream()
                .filter((s) -> s.startsWith("a"))
                .count();
        System.err.println(count);
    }

    /**
     * reduce操作属于最终操作
     * 允许通过指定的函数来将stream中的多个元素规约为一个元素，规约后的结果是通过Optional接口表示的。
     */
    @Test
    public void test6() {
        Optional<String> reduce = data.stream().reduce((x, y) -> x + "#" + y);
        reduce.ifPresent(System.err::print);//a#z#c#e
    }

}
