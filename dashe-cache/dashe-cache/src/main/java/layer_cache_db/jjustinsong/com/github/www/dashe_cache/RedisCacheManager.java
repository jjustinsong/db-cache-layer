package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> T get(String key, Class<T> type) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> void set(String key, T data, long ttl) {
        redisTemplate.opsForValue().set(key, data, ttl, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
