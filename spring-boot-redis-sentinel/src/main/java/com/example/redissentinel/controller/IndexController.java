package com.example.redissentinel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class IndexController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/set/{k}/{v}")
    public String set(@PathVariable String k, @PathVariable String v) {
        try {
            stringRedisTemplate.opsForValue().set(k, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/get/{k}")
    public String get(@PathVariable String k) {
        String v = "";
        try {
            v = stringRedisTemplate.opsForValue().get(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

}
