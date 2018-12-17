package com.example.springbootjava8new.juc;

import org.junit.Test;

/**
 * 模拟CAS算法
 * CAS(Compare-And-Swap) 算法是硬件对于并发的支持,针对多处理器操作而设计的处理器中的一种特殊指令,用于管理对共享数据的并发访问;
 * - CAS 是一种无锁的非阻塞算法的实现;
 * - CAS 包含了三个操作数:
 * 1. 需要读写的内存值: V
 * 2. 进行比较的预估值: A
 * 3. 拟写入的更新值: B
 * 4. 当且仅当 V == A 时, V = B, 否则,将不做任何操作;
 *
 * @author: Quifar
 * @version: 1.0
 */
public class TestCompareAndSwap {

    @Test
    public void test1() {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            // 创建10个线程,模拟多线程环境
            new Thread(() -> {
                Object expectedValue = cas.getValue();
                boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 100));
                System.out.println(b);
            }).start();
        }
    }

}

class CompareAndSwap {
    private volatile Object value;

    // 读取内存中的值
    public synchronized Object getValue() {
        return value;
    }

    // 比较
    public synchronized Object compareAndSwap(Object expectedValue, Object updateValue) {
        Object oldValue = value;

        if (oldValue == expectedValue) {
            this.value = updateValue;
        }

        // 最后返回旧值
        return oldValue;
    }

    // 设置值
    public synchronized boolean compareAndSet(Object expectedValue, Object updateValue) {
        return expectedValue == compareAndSwap(expectedValue, updateValue);
    }
}