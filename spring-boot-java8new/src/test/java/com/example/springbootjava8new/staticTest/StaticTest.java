package com.example.springbootjava8new.staticTest;

import org.junit.Test;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
public class StaticTest {

    @Test
    public void test1() {
        Son son = new Son();
    }

    @Test
    public void test2() {
        System.err.println(100001 % 32);
        System.err.println(100001 & (32 - 1));
    }

    // hashcode相同，而值不同的字符串
    @Test
    public void test3() {
        int hc1 = "ABCDEa123abc".hashCode();
        int hc2 = "ABCDFB123abc".hashCode();
        System.out.println(hc1);  // 165374702
        System.out.println(hc2); //  165374702


        // 效率高
        System.err.println(hc1 ^ (hc1 >>> 16));
        System.err.println(hc2 ^ (hc2 >>> 16));

        System.err.println(hc1 % 16);
        System.err.println(hc1 & (16 - 1));
    }


}
