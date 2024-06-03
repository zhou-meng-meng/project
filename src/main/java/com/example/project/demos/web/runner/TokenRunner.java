package com.example.project.demos.web.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author guanc
 * @version 创建时间：2024/6/3 9:55
 * @describe
 */
@Component
@Order(1)
public class TokenRunner implements ApplicationRunner {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 使用 SCAN 命令匹配所有符合模式的Key
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Set<String> keysToDelete = redisTemplate.keys("USER:LOGIN*");
        // 如果找到了匹配的Key，执行删除操作
        if (!keysToDelete.isEmpty()) {
            redisTemplate.delete(keysToDelete);
        }
    }

}
