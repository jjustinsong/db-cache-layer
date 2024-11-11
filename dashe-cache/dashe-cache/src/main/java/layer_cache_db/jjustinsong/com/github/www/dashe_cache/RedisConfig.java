package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
// Uncomment the following imports if using JSON serialization
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory/*
                                                                                                * , ObjectMapper
                                                                                                * objectMapper
                                                                                                */) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Uncomment the following lines to use JSON serialization
        /*
         * template.setKeySerializer(new StringRedisSerializer());
         * template.setValueSerializer(new
         * GenericJackson2JsonRedisSerializer(objectMapper));
         * 
         * template.setHashKeySerializer(new StringRedisSerializer());
         * template.setHashValueSerializer(new
         * GenericJackson2JsonRedisSerializer(objectMapper));
         */

        template.afterPropertiesSet();
        return template;
    }
}