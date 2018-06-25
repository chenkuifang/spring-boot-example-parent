package com.example.task;

import com.example.service.HandleStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试任务
 * 该任务交由线程池去决定什么时候执行
 *
 * @author QuiFar
 * @version V1.0
 **/
@Slf4j
//@Component 使用  @Bean() 注入了
public class HelloWorldTask implements Runnable {

    private int store;

    @Autowired
    private HandleStoreService handleStoreService;

    public HelloWorldTask() {
    }

    public HelloWorldTask(int store) {
        this.store = store;
    }

    @Override
    public void run() {
        synchronized (HelloWorldTask.class) {
            //handleStoreService.doSomething();
            log.info("store:{}", store--);
        }
    }
}
