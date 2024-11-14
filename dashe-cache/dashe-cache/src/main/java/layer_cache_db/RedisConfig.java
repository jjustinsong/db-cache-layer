package layer_cache_db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Custom RedisTemplate bean with specific key and value serializers.
     *
     * @param connectionFactory The RedisConnectionFactory provided by Spring Boot.
     * @return Configured RedisTemplate.
     * 
     * @Bean
     * @Primary
     *          public RedisTemplate<String, Object>
     *          redisTemplate(RedisConnectionFactory connectionFactory) {
     *          RedisTemplate<String, Object> template = new RedisTemplate<>();
     *          template.setConnectionFactory(connectionFactory);
     * 
     *          // Define the serializers
     *          StringRedisSerializer stringSerializer = new
     *          StringRedisSerializer();
     *          GenericJackson2JsonRedisSerializer jsonSerializer = new
     *          GenericJackson2JsonRedisSerializer();
     * 
     *          // Set the key serializer to StringRedisSerializer
     *          template.setKeySerializer(stringSerializer);
     *          template.setHashKeySerializer(stringSerializer);
     * 
     *          // Set the value serializer to GenericJackson2JsonRedisSerializer
     *          template.setValueSerializer(jsonSerializer);
     *          template.setHashValueSerializer(jsonSerializer);
     * 
     *          // Initialize the template
     *          template.afterPropertiesSet();
     * 
     *          return template;
     *          }
     */

    /**
     * Configures and returns a RedisCacheManager bean.
     *
     * @param connectionFactory The RedisConnectionFactory provided by Spring Boot.
     * @return Configured CacheManager.
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Define serialization for keys and values
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializer<Object> valueSerializer = new GenericJackson2JsonRedisSerializer();

        // Configure cache serialization and TTL
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // Set TTL as needed
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));

        // Build and return the RedisCacheManager
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}
