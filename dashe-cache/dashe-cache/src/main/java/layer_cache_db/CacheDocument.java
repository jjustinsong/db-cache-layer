package layer_cache_db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

@Document
@RedisHash
public class CacheDocument {
    @Id
    private String id;
    private String name;

    // Getters and Setters...

}
