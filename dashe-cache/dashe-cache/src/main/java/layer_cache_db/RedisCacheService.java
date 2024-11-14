package layer_cache_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.Cache;
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

    // private final RedisTemplate<String, Object> redisTemplate;

    private final CacheManager cacheManager;

    @Autowired
    public RedisCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Deletes a cached object by key from a specific cache.
     *
     * @param cacheName The name of the cache.
     * @param key       The cache key.
     */
    public void delete(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            logger.info("Deleted key '{}' from cache '{}'", key, cacheName);
        } else {
            logger.warn("Cache '{}' not found. Cannot delete key '{}'", cacheName, key);
        }
    }

    /**
     * Checks if a key exists in a specific cache.
     *
     * @param cacheName The name of the cache.
     * @param key       The cache key.
     * @return True if the key exists, false otherwise.
     */
    public boolean exists(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            boolean exists = valueWrapper != null;
            logger.info("Key '{}' exists in cache '{}': {}", key, cacheName, exists);
            return exists;
        } else {
            logger.warn("Cache '{}' not found. Cannot check existence of key '{}'", cacheName, key);
            return false;
        }
    }

    /**
     * Sets the expiration time for a key in a specific cache.
     * Note: Spring's Cache abstraction does not provide a direct method to set TTL
     * for individual keys.
     * To set TTL, consider configuring it at the CacheManager or using Redis
     * commands directly.
     *
     * @param cacheName The name of the cache.
     * @param key       The cache key.
     * @param ttl       Time-To-Live in seconds.
     */
    public void expire(String cacheName, String key, long ttl) {
        // Directly setting TTL for a specific key via CacheManager is not
        // straightforward.
        // You may need to access the underlying Redis operations or adjust cache
        // configuration.
        logger.warn(
                "Setting TTL for individual keys is not directly supported with CacheManager. Consider configuring TTL at the cache level.");
    }

    /**
     * Retrieves an object from a specific cache.
     *
     * @param cacheName The name of the cache.
     * @param key       The cache key.
     * @param type      The class type of the object.
     * @param <T>       The type parameter.
     * @return The cached object or null if not found.
     */
    public <T> T getFromCache(String cacheName, String key, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            T value = cache.get(key, type);
            if (value != null) {
                logger.info("Retrieved key '{}' from cache '{}'", key, cacheName);
                return value;
            } else {
                logger.info("Key '{}' not found in cache '{}'", key, cacheName);
                return null;
            }
        } else {
            logger.warn("Cache '{}' not found. Cannot retrieve key '{}'", cacheName, key);
            return null;
        }
    }

    /**
     * Saves an object to a specific cache with an expiration time.
     *
     * @param cacheName    The name of the cache.
     * @param key          The cache key.
     * @param data         The data to cache.
     * @param ttlInSeconds Time-To-Live in seconds.
     * @param <T>          The type parameter.
     */
    public <T> void saveToCache(String cacheName, String key, T data, long ttlInSeconds) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, data);
            logger.info("Saved key '{}' to cache '{}'", key, cacheName);
            // Note: Spring's Cache abstraction manages TTL based on CacheManager's
            // configuration.
            // Setting TTL per key is not directly supported.
        } else {
            logger.warn("Cache '{}' not found. Cannot save key '{}'", cacheName, key);
        }
    }

    /**
     * Removes an object from a specific cache.
     *
     * @param cacheName The name of the cache.
     * @param key       The cache key.
     */
    public void removeFromCache(String cacheName, String key) {
        delete(cacheName, key);
    }

    /**
     * Deletes a cached object by key.
     *
     * @param key The cache key.
     * 
     *            public void delete(String key) {
     *            try {
     *            redisTemplate.delete(key);
     *            } catch (DataAccessException e) {
     *            logger.error("Failed to delete from Redis for key {}: {}", key,
     *            e.getMessage());
     *            }
     *            }
     */

    /*
     * @Autowired
     * public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
     * this.redisTemplate = redisTemplate;
     * }
     */
    /*
     * /**
     * Checks if a key exists in Redis.
     *
     * @param key The cache key.
     * 
     * @return True if the key exists, false otherwise.
     * 
     * public boolean exists(String key) {
     * try {
     * return redisTemplate.hasKey(key);
     * } catch (DataAccessException e) {
     * logger.error("Failed to check existence in Redis for key {}: {}", key,
     * e.getMessage());
     * return false;
     * }
     * }
     */

    /**
     * Sets the expiration time for a key.
     *
     * @param key  The cache key.
     * @param ttl  Time-To-Live in seconds.
     * 
     *             public void expire(String key, long ttl) {
     *             try {
     *             redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
     *             } catch (DataAccessException e) {
     *             logger.error("Failed to set expiration in Redis for key {}: {}",
     *             key, e.getMessage());
     *             }
     *             }
     * 
     *             /**
     *             Retrieves an object from the cache.
     *
     * @param key  The cache key.
     * @param type The class type of the object.
     * @param <T>  The type parameter.
     * @return The cached object or null if not found.
     * 
     *         public <T> T getFromCache(String key, Class<T> type) {
     *         return (T) redisTemplate.opsForValue().get(key);
     *         }
     */

    /**
     * Saves an object to the cache with an expiration time.
     *
     * @param key          The cache key.
     * @param data         The data to cache.
     * @param ttlInSeconds Time-To-Live in seconds.
     * @param <T>          The type parameter.
     */
    /*
     * public <T> void saveToCache(String key, T data, long ttlInSeconds) {
     * redisTemplate.opsForValue().set(key, data, ttlInSeconds, TimeUnit.SECONDS);
     * }
     */

    /**
     * Removes an object from the cache.
     *
     * @param key The cache key.
     * 
     *            public void removeFromCache(String key) {
     *            redisTemplate.delete(key);
     *            }
     */
}
