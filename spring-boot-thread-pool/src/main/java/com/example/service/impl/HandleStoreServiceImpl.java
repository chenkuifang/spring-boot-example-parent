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
    public int subStore(int store) {
        return 1;
    }
}
