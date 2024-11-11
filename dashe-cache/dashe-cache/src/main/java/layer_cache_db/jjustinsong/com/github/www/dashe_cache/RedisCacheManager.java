package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> T get(String key, Class<T> type) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (DataAccessException e) {
            System.err.print("Failed to get in Redis" + e.getMessage());
            return null;
        }
    }

    @Override
    public <T> void set(String key, T data, long ttl) {
        try {
            redisTemplate.opsForValue().set(key, data, ttl, TimeUnit.SECONDS);
        } catch (DataAccessException e) {
            System.err.print("Failed to set in Redis" + e.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (DataAccessException e) {
            System.err.print("Failed to delete in Redis" + e.getMessage());
        }
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void expire(String key, long ttl) {
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }
}