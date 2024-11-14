package layer_cache_db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class CacheDocument {
    @Id
    private String id;
    private String name;

    public CacheDocument() {}

    // Getters and Setters...

    public String toString() {
        return id + ": " + name;
    }

}
