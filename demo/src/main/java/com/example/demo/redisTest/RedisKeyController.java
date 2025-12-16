package com.example.demo.redisTest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Redis Key 操作测试接口
 */
@RestController
@RequestMapping("/redis/key")
public class RedisKeyController {

    private final RedisKeyService redisKeyService;

    public RedisKeyController(RedisKeyService redisKeyService) {
        this.redisKeyService = redisKeyService;
    }

    /**
     * 添加 Key
     * GET /redis/key/add?key=test&value=123&expireSeconds=60
     */
    @GetMapping("/add")
    public String addKey(
            @RequestParam String key,
            @RequestParam String value,
            @RequestParam(required = false) Long expireSeconds
    ) {
        redisKeyService.addKey(key, value, expireSeconds);
        return "Key 添加成功：" + key;
    }

    /**
     * 删除单个 Key
     * GET /redis/key/delete?key=test
     */
    @GetMapping("/delete")
    public String deleteKey(@RequestParam String key) {
        boolean success = redisKeyService.deleteKey(key);
        return success ? "Key 删除成功：" + key : "Key 删除失败（不存在）：" + key;
    }

    /**
     * 批量删除 Key
     * POST /redis/key/delete/batch
     * 请求体：["key1", "key2"]
     */
    @PostMapping("/delete/batch")
    public String deleteKeys(@RequestBody List<String> keys) {
        long count = redisKeyService.deleteKeys(keys);
        return "批量删除成功，共删除 " + count + " 个 Key";
    }

    /**
     * 查询 Key 是否存在
     * GET /redis/key/exists?key=test
     */
    @GetMapping("/exists")
    public String existsKey(@RequestParam String key) {
        boolean exists = redisKeyService.existsKey(key);
        return "Key " + key + (exists ? " 存在" : " 不存在");
    }
}