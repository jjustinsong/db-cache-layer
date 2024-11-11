package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import layer_cache_db.jjustinsong.com.github.www.dashe_cache.User;
import layer_cache_db.jjustinsong.com.github.www.dashe_cache.UserRepository;
// Uncomment if using JSON serialization
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

// import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String USER_CACHE_PREFIX = "user::";

    @Autowired
    private UserRepository userRepository;

    // Uncomment if using JSON serialization
    // @Autowired
    // private ObjectMapper objectMapper;

    public User getUserById(String userId) /* throws JsonProcessingException */ {
        String key = USER_CACHE_PREFIX + userId;

        // Attempt to get user from Redis cache
        User cachedUser = (User) redisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            // Cache Hit
            System.out.println("Cache Hit for user ID: " + userId);
            return cachedUser;
        } else {
            // Cache Miss
            System.out.println("Cache Miss for user ID: " + userId);
            // Retrieve from MongoDB
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                // Store in Redis cache with TTL (e.g., 1 hour)
                // redisTemplate.opsForValue().set(key, user, 1, TimeUnit.HOURS);
                // Without TTL
                redisTemplate.opsForValue().set(key, user);
            }
            return user;
        }
    }
}
