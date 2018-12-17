package com.example.springbootjava8new.juc;

import org.junit.Test;

/**
 * 原子变量测试
 * <p>
 * 一.i++的原子性问题：i++实际上是分为三个步骤：“读-改-写”
 * 如： int i = 10; i = i++;
 * <p>
 * 实际可分为： int tmp = i; i = i + 1; i = tmp;
 *
 * 二、使用原子变量既可解决该问题。原子变量是jkd1.5以后提供的变量，放在java.util.concurrent.atomic包
 *  原子变量具有的特性：2.1 变量都是用了volatile修饰，保证内存可见性
 *                    2.2 使用CAS（compare and swap）算法，保证数据的原子性
 *                      CAS算法：硬件对于并发操作共享资源的支持
 *                      CAS 包含三个操作数
 *                      内存值 V .预估值 A. 更新值 B.
 *                      特性：当且仅当V==A时，才会执行V = B，否则不做任何操作
 * @author: Quifar
 * @version: 1.0
 */
public class AtomicTest {

    // 如果使用volatile 也不能保证数据操作的原子性，问题依然存在。
    private volatile int i = 0;

    @Test
    public void test1() {
        for (int j = 0; j < 100; j++) {
            new Thread(() -> System.err.println(getI())).start();
        }
    }

    public int getI() {
        return i++;
    }
}
