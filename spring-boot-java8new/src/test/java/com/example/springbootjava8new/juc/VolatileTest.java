package com.example.springbootjava8new.juc;

import org.junit.Test;

/**
 * 内存可见，volatile 测试
 * <p>
 * 所有共享的资源，会放在主存中，线程操作的时候会复制到线程的内存中，之后的操作都是在线程自己的
 * 内存中操作，然后在同步到主存中。这就会导致存在并发的线程，获取到初始值一样，各自操作后，同步到
 * 主存中值也是一样的。 如果共享资源使用volatile修饰，可以理解成就是在主存中操作，不存在同步这个操作
 * 的时间差。
 * <p>
 * volatile 相较于synchronized 是一种较为轻量级的同步策略
 * <p>
 * 注意：
 * 1.volatile 不具备"互斥性"
 * 2.volatile 不能保证变量的"原子性"
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

    @Test
    public void test2() {
        ThreadTest td = new ThreadTest();

        // 子线程
        new Thread(td).start();

        // 主线程
        while (true) {
            if (td.getFlag()) {
                System.err.println("###########");
                break;
            }
        }

    }

}


/**
 * test 2
 * 如果共享资源flag 不使用 volatile修改，那么结果是主线程中的flag一直为false.不停的执行
 */
class ThreadTest implements Runnable {
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            // 该线程 sleep(200), 导致了程序无法执行成功
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.err.println("flag=" + getFlag());
    }

    public boolean getFlag() {
        return flag;
    }

}
