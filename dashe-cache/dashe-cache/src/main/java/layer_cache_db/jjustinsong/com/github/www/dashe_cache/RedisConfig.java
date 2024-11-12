package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

    /**
     * Custom RedisTemplate bean with specific key and value serializers.
     *
     * @param connectionFactory The RedisConnectionFactory provided by Spring Boot.
     * @return Configured RedisTemplate.
     */
    @Bean
    @Primary // Marks this bean as the primary bean when multiple candidates are present
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Define the serializers
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        // Set the key serializer to StringRedisSerializer
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // Set the value serializer to GenericJackson2JsonRedisSerializer
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        // Initialize the template
        template.afterPropertiesSet();

        return template;
    }
}
