package com.example.springbootjava8new.juc;

import org.junit.Test;

/**
 * 内存可见，volatile 测试
 * <p>
 * 所有共享的资源，会放在主存中，线程操作的时候会复制到线程的内存中，之后的操作都是在线程自己的
 * 内存中操作，然后在同步到主存中。这就会导致存在并发的线程，获取到初始值一样，各自操作后，同步到
 * 主存中值也是一样的。 如果共享资源使用volatile修饰，可以理解成就是在主存中操作，不存在同步这个操作
 * 的时间差。
 *
 * @author Quifar
 * @version V1.0
 **/
public class VolatileTest {
    private volatile int i = 0;

    @Test
    public void test1() {
        //10个线程操作add方法
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.err.println(Thread.currentThread().getName() + ":" + add()))
                    .start();
        }
    }

    private int add() {
        return i++;
    }
}
