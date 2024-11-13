package layer_cache_db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CacheDocument {
    @Id
    private String id;
    private String name;
    private String email;

    // Getters and Setters...
}
