package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;



import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheManager<T> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisCacheManager(RedisConfig config) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(config.getRedisHost());
        redisConfig.setPort(config.getRedisPort());
        redisConfig.setPassword(config.getRedisPassword());

        JedisConnectionFactory factory = new JedisConnectionFactory(redisConfig);
        factory.afterPropertiesSet();
        
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
    }

    public T get(String key, Class<T> type) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (DataAccessException e) {
            System.err.print("Failed to get in Redis" + e.getMessage());
            return null;
        }
    }

    public void set(String key, T data, long ttl) {
        try {
            redisTemplate.opsForValue().set(key, data, ttl, TimeUnit.SECONDS);
        } catch (DataAccessException e) {
            System.err.print("Failed to set in Redis" + e.getMessage());
        }
    }

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (DataAccessException e) {
            System.err.print("Failed to delete in Redis" + e.getMessage());
        }
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void expire(String key, long ttl) {
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }
}