package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Document(collection = "users")
public class User implements Serializable {
    @Id
    private String id;
    private String name;
    private String email;

    // Constructors
    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    // (omitted for brevity)
}
