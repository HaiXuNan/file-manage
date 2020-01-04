package com.file.manage.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String get(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    public void set(String key, String value, int time) {
        stringRedisTemplate.boundValueOps(key).set(value, time, TimeUnit.SECONDS);
    }

    public boolean exits(String key) {
        return Optional.ofNullable(stringRedisTemplate.hasKey(key)).orElse(false);
    }

    public Long ttl(String key) {
        return stringRedisTemplate.boundValueOps(key).getExpire();
    }

    public boolean delete(String key) {
        return Optional.ofNullable(stringRedisTemplate.delete(key)).orElse(false);
    }

    public void update(String key, String value, int time) {
        if (this.exits(key) && this.delete(key)) {
            this.set(key, value, time);
        }
    }
}
