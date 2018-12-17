package com.example.springbootjava8new.juc;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 复习：创建线程方式有4种
 * 1.继承Thread类，-->重新run() --> 启动：new MyThread().start()
 * 2.实现Runnable接口,--->重写run()--->启动：new Thread(new MyRunnable()).start
 * 3.实现Callable接口(配合ExecutorService使用)，--->重写call()--->创建线程池：ExecutorService executorService = Executors.newFixedThreadPool(5);
 * -->  Future result = executorService.sumbit(new MyCallable()); -->result.get() 获取返回值
 * 4. 当然还可以使用JDK提供的线程池创建方式，一般使用Executors工具类。
 * <p>
 * <p>
 * Executors 工具类测试
 * 什么是任务：
 * 实现Callable接口或Runnable接口的类，其实例就可以成为一个任务提交给ExecutorService去执行,这样的好处是任务和执行解耦。
 * <p>
 * 其中Callable任务可以返回执行结果，Runnable任务无返回结果；
 * <p>
 * <p>
 * 通过Executors工具类可以创建各种类型的线程池，如下为常见的四种：
 * <p>
 * 1.newCachedThreadPool ：大小不受限，当线程释放时，可重用该线程；
 * 2.newFixedThreadPool ：大小固定，无可用线程时，任务需等待，直到有可用线程；
 * 3.newSingleThreadExecutor ：创建一个单线程，任务会按顺序依次执行；
 * 4.newScheduledThreadPool：创建一个定长线程池，支持定时及周期性任务执行
 *
 * @author Quifar
 * @version V1.0
 **/
public class ExecutorsTest {

    /**
     * 创建一个单线程执行器
     */
    @Test
    public void test1() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Task());
        executorService.shutdown();
    }

    class Task implements Runnable {
        @Override
        public void run() {
            System.err.println("running...");
        }
    }

    /**
     * 创建一个指定大小的线程池，并提交一个有返回的任务
     * <p>
     * 使用场景：
     * FutureTask可用于异步获取执行结果或取消执行任务的场景。通过传入Runnable或者Callable的任务给FutureTask
     * ，直接调用其run方法或者放入线程池执行，之后可以在外部通过FutureTask的get方法异步获取执行结果，
     * 因此，FutureTask非常适合用于耗时的计算，主线程可以在完成自己的任务后，
     * 再去获取结果。另外，FutureTask还可以确保即使调用了多次run方法，它都只会执行一次Runnable或者Callable任务
     * ，或者通过cancel取消FutureTask的执行等。
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future result = executorService.submit(new CallBackTask());

        // 模拟主线程执行时间6S
        System.err.println(Thread.currentThread().getName());
        Thread.sleep(6000);

        // 主线程可以在完成自己的任务后，再去获取结果
        System.err.println(result.get()); //  get()方法会使所在线程阻塞，直到返回结果。
        executorService.shutdown();
    }

    /**
     * 注意：get()方法会使所在线程阻塞，直到返回结果。
     */
    class CallBackTask implements Callable<String> {
        @Override
        public String call() {
            try {
                System.err.println(Thread.currentThread().getName());
                Thread.sleep(5000);
                System.err.println(Thread.currentThread().getName() + "执行完成");
            } catch (InterruptedException e) {

            }
            return "I'm callback info!!";
        }
    }

}
