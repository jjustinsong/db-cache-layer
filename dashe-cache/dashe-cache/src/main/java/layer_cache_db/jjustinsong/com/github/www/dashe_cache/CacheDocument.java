package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Document(collection = "documents")
public class CacheDocument implements Serializable {
    @Id
    private String id;
    private String data;

    // Constructors
    public CacheDocument() {
    }

    public CacheDocument(String id, String data) {
        this.id = id;
        this.data = data;
    }

    // Getters and Setters
}
