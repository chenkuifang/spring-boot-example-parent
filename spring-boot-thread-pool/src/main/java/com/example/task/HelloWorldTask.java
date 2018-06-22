package com.example.task;

import com.example.service.HandleStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试任务
 *
 * @author QuiFar
 * @version V1.0
 **/
@Slf4j
@Component
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
            //log.info("hello world");
            //log.info("store:{}", store--);
            store = handleStoreService.subStore(store);
            log.info("store:{}", store);
        }
    }
}
