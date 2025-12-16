package com.example.demo.redisTest;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Redis Key 操作服务类
 */
@Service
public class RedisKeyService {

    // 注入 StringRedisTemplate（Spring 自动配置）
    private final StringRedisTemplate stringRedisTemplate;

    // 构造器注入（推荐）
    public RedisKeyService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 新增 Redis Key（设置值 + 可选过期时间）
     * @param key 键
     * @param value 值
     * @param expireSeconds 过期时间（秒，null 则永久）
     */
    public void addKey(String key, String value, Long expireSeconds) {
        // 设置 key-value
        stringRedisTemplate.opsForValue().set(key, value);
        // 可选：设置过期时间
        if (expireSeconds != null) {
            stringRedisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 删除单个 Redis Key
     * @param key 键
     * @return 是否删除成功
     */
    public boolean deleteKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }

    /**
     * 批量删除 Redis Key
     * @param keys 键列表
     * @return 删除的数量
     */
    public long deleteKeys(Iterable<String> keys) {
        return stringRedisTemplate.delete((Collection<String>) keys);
    }

    /**
     * 查询 Key 是否存在
     * @param key 键
     * @return 是否存在
     */
    public boolean existsKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }
}