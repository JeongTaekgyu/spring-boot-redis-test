package com.example.springbootredis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CacheService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    // redis에 data를 캐싱한다.
    public void cacheData(String key, Object data, long timeoutSeconds) {
        try {
            redisTemplate.opsForValue().set(key, data, timeoutSeconds, TimeUnit.SECONDS);
            log.info("Data cached successfully for key: {}", key);
        } catch (Exception e) {
            log.error("Error caching data: {}", e.getMessage());
            throw new RuntimeException("Cache operation failed", e);
        }
    }

    public <T> Optional<T> getCachedData(String key, Class<T> type) {
        try {
            Object data = redisTemplate.opsForValue().get(key);
            if (data == null) {
                return Optional.empty();
            }
            // redis 에서 가져온 data를 원하는 타입으로 반환한다.
            // of는 값이 무조건 존재하니까 null을 허용하지 않음
            return Optional.of(objectMapper.convertValue(data, type));
        } catch (Exception e) {
            log.error("Error retrieving cached data: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public void deleteCachedData(String key) {
        try {
            redisTemplate.delete(key);
            log.info("Cache deleted successfully for key: {}", key);
        } catch (Exception e) {
            log.error("Error deleting cached data: {}", e.getMessage());
            throw new RuntimeException("Cache deletion failed", e);
        }
    }
}
