package com.example.service.impl;

import com.example.service.HandleStoreService;
import org.springframework.stereotype.Service;

/**
 * 处理库存实现
 *
 * @author QuiFar
 * @version V1.0
 **/
@Service
public class HandleStoreServiceImpl implements HandleStoreService {
    @Override
    public void doSomething() {
        System.err.println(123);
    }
}
