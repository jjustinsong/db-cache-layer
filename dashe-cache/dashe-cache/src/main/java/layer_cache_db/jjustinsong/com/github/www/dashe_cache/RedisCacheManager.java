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
public class RedisCacheManager<T> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Retrieves a cached object by key.
     *
     * @param key  The cache key.
     * @param type The class type of the object.
     * @return The cached object or null if not found.
     */
    public T get(String key, Class<T> type) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value != null && type.isInstance(value)) {
                return type.cast(value);
            }
            return null;
        } catch (DataAccessException e) {
            logger.error("Failed to get from Redis for key {}: {}", key, e.getMessage());
            return null;
        }
    }

    /**
     * Sets a cached object with a specific TTL.
     *
     * @param key  The cache key.
     * @param data The data to cache.
     * @param ttl  Time-To-Live in seconds.
     */
    public void set(String key, T data, long ttl) {
        try {
            redisTemplate.opsForValue().set(key, data, ttl, TimeUnit.SECONDS);
        } catch (DataAccessException e) {
            logger.error("Failed to set in Redis for key {}: {}", key, e.getMessage());
        }
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
}
