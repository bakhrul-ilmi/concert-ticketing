package com.example.ticketing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.connection.RedisHashCommands.HashFieldSetOption;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.params.HSetExParams;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate< String, Object > redisTemplate;

    @Autowired
    public RedisService(RedisTemplate< String, Object > redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public long hlen(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public void hsetex(String key, long seconds, HashMap<String, String> data) {
        redisTemplate.opsForHash().putAndExpire(key, data, HashFieldSetOption.UPSERT, Expiration.seconds(seconds));
    }

    public Boolean hexists(String key, String hashKey){
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public void hdel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }
}
