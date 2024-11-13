package layer_cache_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Service class for managing Redis cache operations.
 *
 * @param <T> The type of data to cache.
 */
@Service
public class RedisCacheService {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheService.class);

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
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

    /**
     * Retrieves an object from the cache.
     *
     * @param key  The cache key.
     * @param type The class type of the object.
     * @param <T>  The type parameter.
     * @return The cached object or null if not found.
     */
    public <T> T getFromCache(String key, Class<T> type) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * Saves an object to the cache with an expiration time.
     *
     * @param key          The cache key.
     * @param data         The data to cache.
     * @param ttlInSeconds Time-To-Live in seconds.
     * @param <T>          The type parameter.
     */
    public <T> void saveToCache(String key, T data, long ttlInSeconds) {
        redisTemplate.opsForValue().set(key, data, ttlInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Removes an object from the cache.
     *
     * @param key The cache key.
     */
    public void removeFromCache(String key) {
        redisTemplate.delete(key);
    }
}
