package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for managing Redis cache operations.
 *
 * @param <T> The type of data to cache.
 */
@Service
public class RedisCacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Deletes a cached object by key.
     *
     * @param key The cache key.
     */
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (DataAccessException e) {
            logger.error("Failed to delete from Redis for key {}: {}", key, e.getMessage());
        }
    }

    /**
     * Checks if a key exists in Redis.
     *
     * @param key The cache key.
     * @return True if the key exists, false otherwise.
     */
    public boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (DataAccessException e) {
            logger.error("Failed to check existence in Redis for key {}: {}", key, e.getMessage());
            return false;
        }
    }

    /**
     * Sets the expiration time for a key.
     *
     * @param key The cache key.
     * @param ttl Time-To-Live in seconds.
     */
    public void expire(String key, long ttl) {
        try {
            redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        } catch (DataAccessException e) {
            logger.error("Failed to set expiration in Redis for key {}: {}", key, e.getMessage());
        }
    }

    public <T> T getFromCache(String key, Class<T> type) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public <T> void saveToCache(String key, T data, long ttlInSeconds) {
        redisTemplate.opsForValue().set(key, data, ttlInSeconds);
    }

    public void removeFromCache(String key) {
        redisTemplate.delete(key);
    }
}
